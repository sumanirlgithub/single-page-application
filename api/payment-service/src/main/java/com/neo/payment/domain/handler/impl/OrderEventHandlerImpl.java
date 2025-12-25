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
import com.neo.payment.exception.TransientProcessingException;
import com.neo.payment.publisher.kafka.KafkaPublisher;
import com.neo.payment.repository.PaymentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandlerImpl implements OrderEventHandler {

    private final PaymentRepository paymentRepository;
    private final KafkaPublisher kafkaPublisher;

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

    @Retry(name = "orderProcessingRetry")
    @CircuitBreaker(name = "orderProcessingCircuitBreaker", fallbackMethod = "fallbackCircuitBreaker")
    private void processPayment(OrderEvent event){
        boolean paymentProcessed = true;
        // ToDo: add payment processing logic here and set paymentProcessed if payment processed successfully ...
        // ToDo: DB / REST / downstream calls

        paymentProcessed= false;

        PaymentEventName paymentEvent = PaymentEventName.FAILED;
        String paymentStatus = "FAILED";
        if (paymentProcessed) {
            paymentEvent = PaymentEventName.COMPLETED;
            paymentStatus = PaymentStatus.COMPLETED.name();
            log.info("Payment record created successfully for the event id: {}.", event.eventId());
        } else {
            log.info("Payment processing failed for the event id: {}.", event.eventId());
            // Simulate CircuitBreaker fallback and send the failed message to DLQ
            throw new RuntimeException("Unable to process payment successfully");
        }
        //kafkaPublisher.createAndSendEvent(new PaymentEvent(paymentEvent, event.orderId(), paymentStatus, Instant.now()));
    }

    /**
     * TransientProcessingException can occur due to Temporary DB outage,
     * Downstream service timeout, Cache unavailable, and Network glitch
     * @param event
     * @param ex
     */
    public void fallback(OrderEvent event, Throwable ex) {
        throw new TransientProcessingException("Circuit open", ex);
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