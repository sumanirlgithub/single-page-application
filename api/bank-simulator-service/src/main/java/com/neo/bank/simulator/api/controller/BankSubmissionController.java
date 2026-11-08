package com.neo.bank.simulator.api.controller;

import com.neo.bank.simulator.api.contract.BankSubmissionResponse;
import com.neo.bank.simulator.api.service.SimulatedBankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-submissions")
@RequiredArgsConstructor
@Slf4j
public class BankSubmissionController {

    private final SimulatedBankService simulatedBankService;

    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BankSubmissionResponse submitDirectDebit(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody String pain008Xml) {

        return simulatedBankService.submit(
                idempotencyKey,
                pain008Xml
        );
    }
}