package com.neo.payment.service;

import com.neo.api.common.enums.PaymentStatus;
import com.neo.payment.connect.client.AuthFeignClient;
import com.neo.payment.connect.response.ConnectAuthResponse;
import com.neo.payment.entity.Payment;
import com.neo.payment.exception.PaymentServiceException;
import com.neo.payment.model.request.PaymentStatusReq;
import com.neo.payment.model.response.PaymentStatusRes;
import com.neo.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Collectors;

import static com.citi.issuer.sharedservices.payment.common.Constants.ERROR_MAX_UETRS_LIMIT;
import static com.neo.payment.exception.ExceptionMessageConstants.CITI_HTTP_CLIENT_AUTH_ERROR;
import static com.neo.payment.exception.ExceptionMessageConstants.EXCEPTION_STRING;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final JmsTemplate jmsTemplate;
    private final AuthFeignClient authFeignClient;
    private final PaymentRepository paymentRepository;
    private final CFService cfService;

    public static final String authPayload = "{\"oAuthToken\":{\"grantType\":\"\"client_credentials\",\"scope\":\"/authenticationservices/v1\"}}";

    @Value("${oneaandt.request.max.uetrs:5}")
    private String maxUetrs;
    @Value("${connect.service.auth.client.id}")
    private String clientId;

    @Value("${connect.service.auth.client.secret}")
    private String clientSecret;

    /**
     * Method to display payment status.
     *
     * @param paymentStatusReq
     * @return PaymentStatusRes
     */
    public List<PaymentStatusRes> getPaymentStatus(PaymentStatusReq paymentStatusReq) {
        long processingStartTime = System.currentTimeMillis();
        log.info("Received request for getPaymentStatus: paymentStatusRequestPayload: {}", paymentStatusReq);
        if (CollectionUtils.isNotEmpty(paymentStatusReq.getUetrNumbers()) && paymentStatusReq.getUetrNumbers().size() > 5) {
            throw new PaymentServiceException(ERROR_MAX_UETRS_LIMIT + maxUetrs);
        }

        String finalAuthToken = getAuthTokenFromConnect();

        List<CompletableFuture<PaymentStatusRes>> completableFutures = new ArrayList<>();
        paymentStatusReq.getUetrNumbers().forEach(uetrNumber -> {
            // get payment status from citi connect api asynchronously
            CompletableFuture<PaymentStatusRes> result = cfService.makeParallelAsyncCallToConnect(uetrNumber, finalAuthToken);
            completableFutures.add(result);
        });
        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

        // When all the Futures are completed, call 'future.join() to get their results and collect the results in list -
        CompletableFuture<List<PaymentStatusRes>> allCompletableFuture = allFutures.thenApply(future -> {
            return completableFutures.stream()
                    .map(completableFuture -> completableFuture.join())
                    .collect(Collectors.toList());
        });

        CompletableFuture completableFuture = allCompletableFuture.thenApply(paymentStatusResList -> {
            return paymentStatusResList.stream().collect(Collectors.toList());
        });
        List<PaymentStatusRes> combinedResponses =  new ArrayList<>();
        try {
            combinedResponses = (List<PaymentStatusRes>) completableFuture.get();
            log.info("Batch of threads completed:" + combinedResponses);
        } catch (Exception e) {
            log.error(EXCEPTION_STRING, e.getMessage());
            throw new PaymentServiceException("Error occurred during processing combined responses");
        }
        long processingEndTime = System.currentTimeMillis();
        log.info("time taken for processing the request: {} milliseconds", (processingEndTime - processingStartTime));
        return combinedResponses;
    }
    @Cacheable("authTokenDetails")
    public String getAuthTokenFromConnect() {
        String authToken = "";
        String requestBody = authPayload;
        Map<String, String> headers = new HashMap<>();
        String auth = Base64.encodeBase64String((clientId + ":" + clientSecret).getBytes()).replaceAll("(\n)", "");
        headers.put("authorization", "Basic " + auth);
        headers.put("content-type", "application/json");
        try {
            log.info("making a call to CitiConnect AuthURL");
            ResponseEntity<ConnectAuthResponse> authResponse = authFeignClient.getAuthTokenFromConnectApi(requestBody, headers);
            authToken = authResponse.getBody().extractAuthToken();
        } catch (Exception e) {
            log.error(EXCEPTION_STRING, e.getMessage());
            throw new PaymentServiceException(CITI_HTTP_CLIENT_AUTH_ERROR);
        }

        if (StringUtils.isEmpty(authToken)) {
            throw new PaymentServiceException("Invalid token received from Citi Connect Auth API");
        }
        return authToken;
    }

    @CacheEvict(value = "authTokenDetails", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.authTokenTTL:1500000}") // milliseconds (empty cache after every 25 minutes)
    public void emptyAuthTokenCache() {
        log.info("emptying Auth token cache");
    }


    @Transactional
    public void savePaymentDetails(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.AUTHORIZED);
        paymentRepository.save(payment);
    }
}
