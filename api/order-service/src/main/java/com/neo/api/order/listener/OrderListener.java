package com.neo.api.order.listener;

import com.neo.api.common.order.event.UpdateOrderStatusEvent;
import com.neo.api.order.entity.PurchaseOrder;
import com.neo.api.order.repository.OrderJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderListener {
    private final OrderJpaRepository orderJpaRepository;

    //@JmsListener(destination = "${order.queue}", containerFactory = "myJmsListenerContainerFactory")
    public void receiveMessage(String message) throws JsonProcessingException {
        log.info("Start receiving a message from Queue: {}", message);
        UpdateOrderStatusEvent updateOrderStatusEvent = new ObjectMapper().readValue(message, UpdateOrderStatusEvent.class);
        // add below the business logic for payment processing.
        PurchaseOrder order = new PurchaseOrder();
        order.setOrderId(String.valueOf(updateOrderStatusEvent.getOrderId()));
        //order.setOrderStatus(updateOrderStatusEvent.getOrderStatus());
        orderJpaRepository.save(order);
    }

}
