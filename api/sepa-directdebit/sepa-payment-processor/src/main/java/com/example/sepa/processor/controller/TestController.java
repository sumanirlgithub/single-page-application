package com.example.sepa.processor.controller;

import com.example.sepa.processor.client.MandateClient;
import com.example.sepa.processor.domain.MandateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * /mandate endpoint is only responsible for creating/registering a SEPA Direct Debit (SDD) mandate
 * The important architectural point is that this API does not create a payment, collection request, pacs.003, transaction, or debit instruction. It only creates and persists the mandate.
 * A clean Spring Boot stack would be: REST Controller → Service → Repository (JdbcTemplate) → Database
 * with the mandate lifecycle kept independent from any later direct-debit collection/payment initiation.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/payment-processor")
public class TestController {

    private final MandateClient mandateClient;

    @GetMapping("/{mandateId}")
    public MandateResponse getMandate(
            @PathVariable String mandateId) {
        // -----------------------------------------
        // 3. Get mandate from Mandate Service
        // -----------------------------------------

        return mandateClient.getMandate(mandateId);
    }

}