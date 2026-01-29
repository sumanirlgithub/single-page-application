package com.neo.payment.service;

import com.neo.api.common.enums.PaymentStatus;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.payment.dto.PaymentInsightRequest;
import com.neo.payment.dto.PaymentInsightResponse;
import com.neo.payment.dto.PaymentRequest;
import com.neo.payment.dto.PaymentResponse;
import com.neo.payment.entity.Payment;
import com.neo.payment.exception.TransientProcessingException;
import com.neo.payment.repository.PaymentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PaymentGatewayClient {

    private final WebClient webClient;
    private final PaymentRepository paymentRepository;


    public PaymentGatewayClient(WebClient.Builder builder, PaymentRepository paymentRepository) {
        this.webClient = builder
                .baseUrl("http://localhost:8086")
                .build();

        this.paymentRepository = paymentRepository;
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


    @Retry(name = "orderProcessingRetry")
    @CircuitBreaker(
            name = "orderProcessingCircuitBreaker",
            fallbackMethod = "paymentStatusFallbackCircuitBreakerPureReactive"
    )
    public Mono<Void> callPaymentGatewayExternalUsingWebFluxPureReactiveAsync(
            List<PaymentInsightRequest> requests) {
        return Flux.fromIterable(requests)
                .flatMap(this::callPaymentGateway, 5)// 5 is the concurrency limit. It tells reactor - Starts 5 WebClient calls, Waits until one completes, Starts the 6th. Always keeps ≤ 5 in-flight calls. Think of it like a semaphore or connection poo
                .flatMap(this::savePaymentStatusInDbReactive)
                .doOnNext(p -> log.info("Saved payment {}", p.getPaymentId()))
                .then();
    }

    private Mono<PaymentInsightResponse> callPaymentGateway(
            PaymentInsightRequest request) {
        log.info("Calling the external REST API in non-blocking reactive way");
        return webClient.post()
                .uri("/api/payment-gateways-service/payments/status")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentInsightResponse.class);
    }

    private Mono<Payment> savePaymentStatusInDbReactive(
            PaymentInsightResponse resp) {
        log.info("Calling JPA repository save method in non-blocking reactive way");
        return Mono.fromCallable(() -> {
                UUID paymentId = UUID.fromString(resp.getPaymentNumber());
                Payment payment = paymentRepository.findById(paymentId)
                        .orElse(null);
                if (payment == null) {
                    log.warn("Payment not found for {}", paymentId);
                    return null;
                }
                payment.setPaymentStatus(
                        PaymentStatus.valueOf(resp.getStatus())
                );
                return paymentRepository.save(payment);
            })
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(Mono::justOrEmpty);
    }

    public Mono<Void> paymentStatusFallbackCircuitBreakerPureReactive(
            List<PaymentInsightRequest> requests, Throwable ex) {

        log.error("PaymentFallback triggered for {} requests",
                requests.size(), ex);

        return Mono.error(
                new TransientProcessingException("Payment service unavailable", ex)
        );
    }

    @Retry(name = "orderProcessingRetry")
    @CircuitBreaker(
            name = "orderProcessingCircuitBreaker",
            fallbackMethod = "paymentStatusFallbackCircuitBreaker"
    )
    public Mono<List<PaymentInsightResponse>> callPaymentGatewayExternalUsingWebFluxReactive(List<PaymentInsightRequest> requests) {
        return Flux.fromIterable(requests)
                .flatMap(request -> webClient.post()
                        .uri("/api/payment-gateways-service/payments/status")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(PaymentInsightResponse.class)
                )
                .collectList(); // returns Mono<List<PaymentInsightResponse>>
    }

    /**
     * TransientProcessingException can occur due to Temporary DB outage,
     * Downstream service timeout, Cache unavailable, and Network glitch
     * @param requests
     * @param ex
     */
    public Mono<List<PaymentInsightResponse>> paymentStatusFallbackCircuitBreaker(List<PaymentInsightRequest> requests, Throwable ex) {
        log.error("PaymentFallback triggered: requests={}, reason={}", requests, ex.getMessage());
        throw new TransientProcessingException("Payment service unavailable", ex);
    }

    @Retry(name = "orderProcessingRetry")
    @CircuitBreaker(
            name = "orderProcessingCircuitBreaker",
            fallbackMethod = "paymentStatusFallbackCircuitBreaker"
    )
    public List<PaymentInsightResponse> callPaymentGatewayExternalUsingWebFluxBlocking(List<PaymentInsightRequest> requests) {
        List<PaymentInsightResponse> responses = new ArrayList<>();
        requests.forEach(request -> {
            PaymentInsightResponse response =
                    webClient.post()
                            .uri("/api/payment-gateways-service/payments/status")
                            .bodyValue(request)
                            .retrieve()
                            .bodyToMono(PaymentInsightResponse.class)
                            .block(); // WAIT here, blocking / synchronous call
            responses.add(response);
        });
        return responses;
    }

    /**
     * TransientProcessingException can occur due to Temporary DB outage,
     * Downstream service timeout, Cache unavailable, and Network glitch
     * @param request
     * @param ex
     */
    public Mono<List<PaymentInsightResponse>> paymentStatusFallbackCircuitBreakerBlocking(PaymentInsightRequest request, Throwable ex) {
        log.error("PaymentFallback triggered: requests={}, reason={}", request, ex.getMessage());
        throw new TransientProcessingException("Payment service unavailable", ex);
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
