package com.example.mandate.controller;

import com.example.mandate.dto.request.CreateMandateRequest;
import com.example.mandate.dto.response.MandateResponse;
import com.example.mandate.service.MandateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * POST /api/v1/mandates Create a mandate
 * GET /api/v1/mandates/{mandateId} Retrieve mandate details
 * PATCH or POST /api/v1/mandates/{mandateId}/status
 */

/**
 * /mandate endpoint is only responsible for creating/registering a SEPA Direct Debit (SDD) mandate
 * The important architectural point is that this API does not create a payment, collection request, pacs.003, transaction, or debit instruction. It only creates and persists the mandate.
 * A clean Spring Boot stack would be: REST Controller → Service → Repository (JdbcTemplate) → Database
 * with the mandate lifecycle kept independent from any later direct-debit collection/payment initiation.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/mandates")
public class MandateController {

    private final MandateService mandateService;

    @GetMapping("/{mandateId}")
    @PreAuthorize("hasRole('API') or hasRole('ADMIN')")
    public MandateResponse getMandate(
            @PathVariable String mandateId) {
        return mandateService.getMandate(mandateId);
    }

    @PostMapping(value = "/submit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MandateResponse> createMandate(
            @Valid @RequestBody CreateMandateRequest request) {

        MandateResponse response =
                mandateService.createMandate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}