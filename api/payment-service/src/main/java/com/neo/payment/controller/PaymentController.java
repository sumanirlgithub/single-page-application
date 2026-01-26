package com.neo.payment.controller;

import com.neo.payment.common.JwtTokenUtil;
import com.neo.payment.dto.PaymentInsightRequest;
import com.neo.payment.model.response.PaymentStatusRes;
import com.neo.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/protected")
public class PaymentController {

    private final PaymentService paymentService;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Endpoint to get Payment Insight Status for single payment.
     *
     * @param paymentStatusReq paymentStatusReq
     */
    @PostMapping(value = "/status")
    @CrossOrigin(origins = "http://localhost:4209")
    public ResponseEntity<List<PaymentStatusRes>> getPaymentStatus(
            @Valid
            @RequestBody PaymentInsightRequest paymentStatusReq,
            @RequestHeader("Authorization") String token) {
        //) {
        // Validate the JWT token
//        if (jwtTokenUtil.validate(token)) {
//            // Process the request
//            return ResponseEntity.ok("Request Authorized");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token received"));
//        }

        log.info("Received request for getPaymentStatus: paymentStatusRequestPayload: {}", paymentStatusReq);
        //return ResponseEntity.ok(paymentService.getPaymentStatus(paymentStatusReq));
        PaymentStatusRes response = new PaymentStatusRes();
        response.setUetrNumber("6f7a55fb-6ef1-4a26-ba66-a812554937d8");
        response.setStatusCode("ACSC");
        response.setStatus("Completed");
        return ResponseEntity.ok(Arrays.asList(response));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello!");
    }

    @GetMapping("/user")
    //@PreAuthorize("hasRole('role_user')")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello From User!");
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('role_admin')")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello From Admin!");
    }
}
