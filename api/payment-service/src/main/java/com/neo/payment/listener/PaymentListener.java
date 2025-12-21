package com.neo.payment.listener;

import com.neo.api.common.enums.OrderStatus;
import com.neo.payment.connect.response.PaymentEvent;
import com.neo.payment.entity.Payment;
import com.neo.payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {

//    private final JmsTemplate jmsTemplate;
//    private final PaymentService paymentService;
//
//    @JmsListener(destination = "${payment.queue}", containerFactory = "myJmsListenerContainerFactory")
//    public void receiveMessage(String message) throws JsonProcessingException {
//        log.info("Start receiving a message from Queue: {}",message);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//        PaymentEvent paymentEvent = mapper.readValue(message, PaymentEvent.class);
//        try {
//            // store the payment event in database and add below the business logic for processing payment .
//            Payment payment = new Payment();
//            payment.setOrderId(paymentEvent.getOrderId());
//            paymentService.savePaymentDetails(payment);
//
//            // Send payment details to external PSP (stripe, PayPal)
//
//            // then external PSP send card details to Card scheme (VISA or mastercard)
//            // after the PSP successfully process the payment, the coordinator-service update the wallet-service
//
//            // if everything fine, during executing the above business logic for payment processing then call
//            // confirmPayment method otherwise the flow will go to catch block
//            updateOrderStatusEvent(paymentEvent.getOrderId(), OrderStatus.COMPLETED);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            updateOrderStatusEvent(paymentEvent.getOrderId(), OrderStatus.CANCELLED);
//        }
//    }
//
//    private void updateOrderStatusEvent(String orderId, OrderStatus orderStatus) throws JsonProcessingException {
//        UpdateOrderStatusEvent updateOrderStatusEvent = UpdateOrderStatusEvent.builder()
//                .orderId(orderId)
//                .orderStatus(orderStatus)
//                .build();
//        // Create and send event to order-service for updating order status via JMS Queue / jmsTemplate
//        String jmsMessage = new ObjectMapper().writeValueAsString(updateOrderStatusEvent);
//        jmsTemplate.convertAndSend("order-queue", jmsMessage);
//    }
}
