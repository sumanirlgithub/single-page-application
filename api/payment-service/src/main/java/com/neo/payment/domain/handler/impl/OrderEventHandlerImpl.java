package com.neo.payment.domain.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neo.api.common.avro.model.generated.OrderAvroEvent;
import com.neo.api.common.enums.PaymentEventName;
import com.neo.api.common.enums.PaymentStatus;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.api.common.payment.event.PaymentEvent;
import com.neo.payment.domain.handler.OrderEventHandler;
import com.neo.payment.dto.PaymentRequest;
import com.neo.payment.dto.PaymentResponse;
import com.neo.payment.exception.TransientProcessingException;
import com.neo.payment.publisher.kafka.KafkaPublisher;
import com.neo.payment.repository.PaymentRepository;
import com.neo.payment.service.PaymentGatewayClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.SocketException;
import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandlerImpl implements OrderEventHandler {

    private final PaymentRepository paymentRepository;
    private final KafkaPublisher kafkaPublisher;
    private final WebClient webClient;
    private final PaymentGatewayClient paymentGatewayClient;

    @Override
    public void onEvent(OrderEvent event) throws SocketException, JsonProcessingException {
        switch (event.eventType()) {
            case ORDER_CREATED:
                processPayment(event);
                //createShipment(event);
                break;
            case ORDER_COMPLETED:
                updateShipment(event);
                break;
            default:
                throw new RuntimeException("unsupported event");
        }
    }

    public void processPayment(OrderEvent event){
        // ToDo: add payment processing logic here and set paymentProcessed if payment processed successfully ...
        // ToDo: DB / REST / downstream calls

        log.info("Calling payment-gateways-service");
        PaymentResponse response = paymentGatewayClient.callPaymentGatewayUsingWebClientBlocking(event);


        // Simulate CircuitBreaker fallback and send the failed message to DLQ
        // by bringing down payment-gateways-service

        log.info("Sending payment event to KAFKA topic");
        //kafkaPublisher.createAndSendEvent(new PaymentEvent(paymentEvent, event.orderId(), paymentStatus, Instant.now()));
    }

    /**
     * Reactive (non-blocking)
     * WebClient sends the HTTP request, Response is received asynchronously
     * response is now available inside the lambda
     *  not recommended in this case though as we have to commit kafka offset after each successful call to payment-gateways-service
     */
    private void callPaymentGatewaysAPiUsingWebClientNonBlocking(OrderEvent event) {
        PaymentRequest paymentRequest = new PaymentRequest();
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8086") // or payment gateway host
                .build();


        Mono<PaymentResponse> responseMono = webClient.post()
                    .uri("/api/payment-gateways-service/payments/process")
                    .bodyValue(paymentRequest)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class);

        responseMono.subscribe(
                response -> {
                    // success path
                    log.info("Received Payment Status: {}", response.getStatus());
                },
                error -> {
                    // error path
                    log.error("Error calling payment service", error);
                }
        );
    }

    private void createShipment(OrderEvent event) {
    }

    private void updateShipment(OrderEvent event) {
//        log.info("update shipment by = {}.", event);
//        paymentRepository.findById((String) event.getBody().getId()).ifPresentOrElse(
//                shipmentRecord -> {
//                    shipmentRecord.setState(ShipmentEventName.CREATED);
//                    shipmentRepository.save(shipmentRecord);
//                    log.info("update success. id = {}", shipmentRecord.getShipmentId());
//                },
//                () -> log.warn("shipment not found bby id = {}", event.getBody().getId()));
    }


    @Override
    public void onRequeueEvent(OrderEvent event) {

    }
    public void onEvent(OrderAvroEvent event) {
        switch (event.getBody().getOrderEventName()) {
            case CREATED:
                //createShipment(event);
                break;
            case COMPLETED:
                //updateShipment(event);
                break;
            default:
                throw new RuntimeException("unsupported event");
        }
    }

    @Override
    public void onRequeueEvent(OrderAvroEvent event) {

    }

}