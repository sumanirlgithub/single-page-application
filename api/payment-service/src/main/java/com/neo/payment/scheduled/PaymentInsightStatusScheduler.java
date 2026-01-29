package com.neo.payment.scheduled;

import com.google.common.collect.Lists;
import com.neo.api.common.enums.PaymentStatus;
import com.neo.payment.dto.PaymentInsightRequest;
import com.neo.payment.dto.PaymentInsightResponse;
import com.neo.payment.entity.Payment;
import com.neo.payment.repository.PaymentRepository;
import com.neo.payment.service.PaymentGatewayClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentInsightStatusScheduler {

    // Number of uetr to send in each request
    @Value("${payment-gateway-external.requestLimit: 5}")
    private int paymentApiRequestLimit;

    //Number of concurrent calls to make to shared services
    @Value("${payment-gateway-external.concurrentRequestLimit: 4}")
    private int paymentApiConcurrentRequestLimit;

    @Value("${payment-gateway-external.pageSize: 100}")
    private int pageSize;

    private final PaymentGatewayClient paymentGatewayClient;

    private final PaymentRepository paymentRepository;

    // runs at 00 and 30 minutes of every hour
    @Scheduled(cron = "${poller.auto-refresh-pmt-status-cron: 0 0/30 * * * *}")
    @Scheduled(fixedDelayString = "${job.delay.ms: 30000}")
    @ConditionalOnProperty(name = "${feature.pmt-insight-flag}", havingValue = "true")
    public void refreshRlsPmtStatusPoller() {
        log.info("Poller started for auto refresh of payment status");
        List<PaymentInsightRequest> requests = new ArrayList<>();
        PaymentInsightRequest request1 = new PaymentInsightRequest();
        request1.setPaymentNumber(UUID.fromString("79e1b67f-fa83-11f0-a75f-98e743c18bd4"));
        PaymentInsightRequest request2 = new PaymentInsightRequest();
        request2.setPaymentNumber(UUID.fromString("7b194d23-fa83-11f0-a75f-98e743c18bd4"));
        requests = Arrays.asList(request1, request2);

        log.info("Calling Payment Insight Service for {} requests", requests);
        paymentGatewayClient.callPaymentGatewayExternalUsingWebFluxPureReactiveAsync(requests)
                .subscribe(); // subscribe at the boundary (controller or scheduler)

        //List<PaymentInsightResponse> responses = paymentGatewayClient.callPaymentGatewayExternalUsingWebFluxPureReactiveAsync(requests)
        //        .block(); // blocking
        //log.info("Aggregated results from the external service: " + responses);
        //assert responses != null;
        //savePaymentStatusInDb(responses, true);

        log.info("Poller completed for auto refresh of payment status for all records");
    }

    private void savePaymentStatusInDb(List<PaymentInsightResponse> responses, boolean isAsync) {
        responses.forEach(res -> {
            Payment payment = paymentRepository.findById(UUID.fromString(res.getPaymentNumber()))
                    .orElseThrow(() -> new RuntimeException("Payment Not Found: " + res.getPaymentNumber()));
            payment.setPaymentStatus(PaymentStatus.valueOf(res.getStatus()));
            paymentRepository.save(payment);
        });
    }

    // runs at 00 and 30 minutes of every hour
    //@Scheduled(cron = "${poller.auto-refresh-pmt-status-cron: 0 0/30 * * * *}")
    @ConditionalOnProperty(name = "${feature.pmt-insight-flag}", havingValue = "true")
    public void refreshRlsPmtStatusPollerSuman() {
        log.info("Poller started for auto refresh of payment status");
        AtomicInteger pageNumber = new AtomicInteger();
        List<String> paymentNumbers;
        int cnt = 0;
        do {
            //uetrNumbers = paymentReleaseRepository.findByPaymentDateAndPmtStatusAndPmtInsightStatus(
            //        prevDate, currentDate, PaymentInsightStatus.RETURN_REJECTS.getCode(), PageRequest.of(pageNumber.get(), pageSize));

            paymentNumbers = Arrays.asList("123", "456");

            log.info("Fetch {} number of released payments for Returns and Rejects status", paymentNumbers.size());
            List<List<String>> uetrSubList = Lists.partition(paymentNumbers, paymentApiRequestLimit);
            List<PaymentInsightRequest> requestList = uetrSubList.stream()
                    .map(uetrs -> {
                        PaymentInsightRequest request = new PaymentInsightRequest();
                        // .setUetrNumbers(uetrs);
                        return request;
                    }).toList();
            List<List<PaymentInsightRequest>> subList = Lists.partition(requestList, paymentApiConcurrentRequestLimit);
            List<String> finalUetrNumbers = paymentNumbers;
            subList.forEach(sub -> {
                log.info("Calling Payment Insight Service for {} requests", sub.size());
                //List<PaymentInsightResponse> responses = paymentGatewayClient.callPaymentGatewayExternalUsingWebFlux(sub.getFirst());
                pageNumber.addAndGet(1);
                //log.info("responses:" + responses);
                log.info("Poller for auto refresh of payment status for pageNumber {} with " + "{} records completed", pageNumber, finalUetrNumbers.size());
            });
            //} while (!paymentNumbers.isEmpty());
            cnt ++;
        } while (cnt <= 1);

        log.info("Poller completed for auto refresh of payment status for all records");
    }

}
