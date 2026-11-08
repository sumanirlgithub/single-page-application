package com.neo.payments.hub.api.controller;

import com.neo.payments.hub.business.service.PaymentWorkflowService;
import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.model.payment.Payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentWorkflowService paymentWorkflowService;

    @PostMapping("/submit")
    public ResponseEntity<Payment> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        Payment payment = paymentWorkflowService.createPayment(
                request,
                request.paymentMethod(),
                request.submissionId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(payment);
    }
}