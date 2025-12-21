package com.neo.payment.domain.handler.impl;

import com.neo.api.common.enums.PaymentEventName;
import com.neo.api.common.enums.PaymentStatus;
import com.neo.api.common.order.event.InventoryReservedEvent;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.api.common.payment.event.PaymentEvent;
import com.neo.payment.domain.handler.InventoryEventHandler;
import com.neo.payment.publisher.kafka.KafkaPublisher;
import com.neo.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.net.SocketException;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryEventHandlerImpl implements InventoryEventHandler {

    private final PaymentRepository paymentRepository;

    private final KafkaPublisher kafkaPublisher;
    private final KafkaTemplate kafkaTemplate;

    @Override
    public void onEvent(InventoryReservedEvent event) throws SocketException {
        switch (event.eventName()) {
            case INVENTORY_RESERVED:
                processPayment(event);
                //createShipment(event);
                break;
            case INVENTORY_FAILED:
                //updateShipment(event);
                break;
            default:
                throw new RuntimeException("unsupported event");
        }
    }
    private void processPayment(InventoryReservedEvent event){
        PaymentEventName paymentEvent = PaymentEventName.FAILED;
        String paymentStatus = PaymentStatus.FAILED.name();
        try {
            // ToDo: add payment processing logic here...
            //paymentService.charge(event.orderId(), event.totalAmount());
            log.info("Payment record created successfully for the order id: {}.", event.orderId());
            //kafkaPublisher.createAndSendEvent(new PaymentEvent(event.orderId(), paymentEvent, paymentStatus, Instant.now()));
            kafkaTemplate.send("PAYMENT-COMPLETED-TOPIC", new PaymentEvent(PaymentEventName.COMPLETED, event.orderId(),
                    PaymentStatus.COMPLETED.name(), event.createdDate()));
        } catch (Exception e) {
            kafkaTemplate.send("PAYMENT-FAILED-TOPIC", new PaymentEvent(PaymentEventName.FAILED, event.orderId(),
                    PaymentStatus.FAILED.name(), event.createdDate()));
        }
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
    public void onRequeueEvent(InventoryReservedEvent event) {

    }
}