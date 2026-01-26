package com.neo.api.order.subscriber.kafka;

import com.neo.api.order.entity.PurchaseOrder;
import com.neo.api.order.events.PaymentAuthorizedEvent;
import com.neo.api.order.events.PaymentFailedEvent;
import com.neo.api.order.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final OrderJpaRepository orderJpaRepository;

    @EventListener
    public void onPaymentAuthorized(PaymentAuthorizedEvent event) {
        //PurchaseOrder order = orderJpaRepository.findById(event.orderId());
       // order.confirm(event.transactionId());
        //orderJpaRepository.save(order);
    }

    @EventListener
    public void onPaymentFailed(PaymentFailedEvent event) {
        //PurchaseOrder order = orderJpaRepository.findById(event.orderId());
        //order.fail(event.reason());
        //orderJpaRepository.save(order);
    }
}
