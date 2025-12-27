package com.neo.api.order.controller;

import com.neo.api.order.dto.PaymentStatusResult;
import com.neo.api.order.model.PaymentInsightRequest;
import com.neo.api.order.model.PaymentInsightResponse;
import com.neo.api.order.service.PaymentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RefreshScope
@RequestMapping("/payments")
public class PaymentStatusController {
    private final PaymentClient paymentClient;

    @PostMapping("/status")
    public ResponseEntity<List<PaymentStatusResult>> getPaymentStatuses(@RequestBody PaymentInsightRequest request) throws Exception {
        return paymentClient.fetchPaymentStatuses(request.getUetrNumbers());
    }

    @PostMapping("/one-payment-status")
    public ResponseEntity<List<PaymentInsightResponse>> manualRefreshPaymentStatus(@RequestParam String paymentNumber) throws Exception {
        return paymentClient.manualRefreshPaymentStatus(paymentNumber);
    }


}
