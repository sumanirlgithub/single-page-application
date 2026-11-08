package com.neo.payments.hub.api.controller;

import com.neo.payments.hub.api.util.ResponseUtils;
import com.neo.payments.hub.contract.RestAPIResponse;
import com.neo.payments.hub.contract.directdebits.DirectDebitsDTO;
import com.neo.payments.hub.contract.exception.PaymentsHubServiceException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"restful/directdebits"})
@Slf4j
public class DirectDebitController {

    @PostMapping(value = "/submit")
    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasRole('API') or hasRole('ADMIN')")
    public ResponseEntity<RestAPIResponse> postDirectDebits(@Valid @RequestBody DirectDebitsDTO directDebitsDTO) throws PaymentsHubServiceException {

        log.info("Direct Debits are created successfully");
        Map<Integer, DirectDebitsDTO> dataMap = new HashMap<>();
        dataMap.put(1, directDebitsDTO);
        return ResponseEntity.ok(ResponseUtils.success(dataMap, "Direct Debits are created successfully"));
    }
}
