package com.neo.payment.service;

import com.neo.api.common.order.event.OrderEvent;
import com.neo.payment.dto.PaymentRequest;
import com.neo.payment.dto.PaymentResponse;
import com.neo.payment.exception.TransientProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PaymentGatewayClient {

    private final WebClient webClient;

    public PaymentGatewayClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8086")
                .build();
    }

    @Retry(name = "orderProcessingRetry")
    @CircuitBreaker(
            name = "orderProcessingCircuitBreaker",
            fallbackMethod = "paymentFallbackCircuitBreaker"
    )
    public PaymentResponse callPaymentGatewayUsingWebClientBlocking(OrderEvent event) {

        PaymentRequest request = new PaymentRequest();
        // populate request from event

        return webClient.post()
                .uri("/api/payment-gateways-service/payments/process")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block(); // WAIT here
    }

    /**
     * TransientProcessingException can occur due to Temporary DB outage,
     * Downstream service timeout, Cache unavailable, and Network glitch
     * @param event
     * @param ex
     */
    public PaymentResponse paymentFallbackCircuitBreaker(
            OrderEvent event,
            Throwable ex
    ) {
        log.error("PaymentFallback triggered: eventId={}, reason={}", event.eventId(), ex.getMessage());
        throw new TransientProcessingException("Payment service unavailable", ex);
    }
}

