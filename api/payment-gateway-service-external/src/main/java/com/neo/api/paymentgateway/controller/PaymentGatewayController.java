package com.neo.api.paymentgateway.controller;

import com.neo.api.paymentgateway.dto.PaymentRequest;
import com.neo.api.paymentgateway.dto.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RefreshScope
@RequestMapping("/payments")
public class PaymentGatewayController {

    /**
     *
     * Endpoint process payment.
     */
    @PostMapping("/process")
    public PaymentResponse process(
            //@Valid
            @RequestBody PaymentRequest req) {
        // blocking logic, maybe calls DB
        return PaymentResponse.builder().status("SUCCESS").build();
    }
}
