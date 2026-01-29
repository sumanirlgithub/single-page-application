package com.neo.api.paymentgateway.controller;

import com.neo.api.paymentgateway.dto.PaymentInsightRequest;
import com.neo.api.paymentgateway.dto.PaymentInsightResponse;
import com.neo.api.paymentgateway.dto.PaymentRequest;
import com.neo.api.paymentgateway.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RefreshScope
@RequestMapping("/payments")
public class PaymentGatewayController {

    private static final Map<UUID, PaymentInsightResponse> RESPONSES =
            Map.of(
                    UUID.fromString("79e1b67f-fa83-11f0-a75f-98e743c18bd4"),
                    PaymentInsightResponse.builder()
                            .paymentNumber(UUID.fromString("79e1b67f-fa83-11f0-a75f-98e743c18bd4"))
                            .status("AUTHORIZED")
                            .build(),

                    UUID.fromString("7b194d23-fa83-11f0-a75f-98e743c18bd4"),
                    PaymentInsightResponse.builder()
                            .paymentNumber(UUID.fromString("7b194d23-fa83-11f0-a75f-98e743c18bd4"))
                            .status("AUTHORIZED")
                            .build()
            );

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

    /**
     *
     * Endpoint get payment status for a payment number.
     */
    @PostMapping("/status")
    public PaymentInsightResponse getStatus(@RequestBody PaymentInsightRequest request) {
        // blocking logic, maybe calls DB
        log.info("request.getPaymentNumber(): " + request.getPaymentNumber());
        UUID paymentNumber = request.getPaymentNumber();
        log.info("paymentNumber: " + paymentNumber);
        return Optional.ofNullable(RESPONSES.get(paymentNumber))
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown payment number: " + paymentNumber));
    }

//    /**
//     *
//     * Endpoint get payment status for a payment number.
//     * This method is blocking and non-reactive using WebFlux - Reactive stack (WebFlux + R2DBC)
//     */
//    @PostMapping("/status")
//    public Mono<PaymentInsightResponse> getStatus(@RequestBody UUID paymentNumber) {
//        return Mono.justOrEmpty(RESPONSES.get(paymentNumber))
//                .switchIfEmpty(Mono.error(
//                        new IllegalArgumentException("Unknown payment number: " + paymentNumber)
//                ));
//    }
//

}
