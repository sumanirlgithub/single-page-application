package com.neo.payment.service;

import com.neo.payment.connect.client.PaymentFeignClient;
import com.neo.payment.connect.request.ConnectPaymentStatusRequest;
import com.neo.payment.connect.response.ConnectPaymentStatusResponse;
import com.neo.payment.connect.response.PaymentTransaction;
import com.neo.payment.connect.response.StatusReasonInfo;
import com.neo.payment.model.response.PaymentStatusRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CFServiceImpl implements CFService {

    private final ExecutorService paymentStatusExecutor;

    private final PaymentFeignClient paymentFeignClient;

    @Override
    public CompletableFuture<PaymentStatusRes> makeParallelAsyncCallToConnect(String uetrNumber, String finalAuthToken) {
        CompletableFuture<PaymentStatusRes> result = getPaymentStatusResponseFromCitiConnect(uetrNumber, finalAuthToken);
        return result;
    }

    private CompletableFuture<PaymentStatusRes> getPaymentStatusResponseFromCitiConnect(String uetrNumber, String finalAuthToken) {
        CompletableFuture<PaymentStatusRes> completableFuture = CompletableFuture.supplyAsync(
                () -> invokeConnectPaymentStatusApi(uetrNumber, finalAuthToken), paymentStatusExecutor);
        return completableFuture;
    }

    private PaymentStatusRes invokeConnectPaymentStatusApi(String uetrNumber, String finalAuthToken) {
        Map<String, String> fullHeaders = new HashMap<>();
        fullHeaders.put("authorization", "Bearer " + finalAuthToken);
        fullHeaders.put("content-type", "application/json");
        fullHeaders.put("accept", "application/json");

        ConnectPaymentStatusRequest citiConnectRequest = new ConnectPaymentStatusRequest(uetrNumber);
        ConnectPaymentStatusResponse citiConnectResponse =
                paymentFeignClient.getPaymentStatusFromCitiConnectApi(citiConnectRequest, fullHeaders);
        log.info("Success response received from CitiConnect is : " + citiConnectResponse.toString());
        return populatePaymentStatusItem(citiConnectResponse.getTransactions().get(0));
    }
    private PaymentStatusRes populatePaymentStatusItem(PaymentTransaction transaction) {
        PaymentStatusRes paymentStatusRes = PaymentStatusRes.builder().build();
        Optional<StatusReasonInfo> statusReasonInfo;
        if (transaction.getTransactionStatus() != null
                && transaction.getTransactionStatus().getStatusReasonInfoList() != null) {
            statusReasonInfo = transaction.getTransactionStatus().getStatusReasonInfoList()
                    .stream().findFirst();
            String reason = statusReasonInfo.isPresent() ? statusReasonInfo.get().getReason() : String.valueOf(Optional.empty());
            String reasonDescription = statusReasonInfo.isPresent() ? statusReasonInfo.get().getReason_description() : String.valueOf(Optional.empty());
            String additionalInfo = transaction.getPaymentEvent().getAdditionalRemittanceInfo().getConfidential();

            paymentStatusRes = PaymentStatusRes.builder()
                    .uetrNumber(transaction.getUetr())
                    .statusCode(transaction.getTransactionStatus().getStatus())
                    .reasonCode(reason)
                    .status("Processing")
                    .reasonCode("Received For Processing")
                    .build();
        }
        return paymentStatusRes;
    }
}
