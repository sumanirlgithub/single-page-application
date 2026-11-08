package com.neo.payments.hub.dao.payment.strategy;

import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PaymentDetailsStrategyRegistry {

    private final List<PaymentDetailsStrategy<?>> strategies;

    public PaymentDetailsStrategyRegistry(
            List<PaymentDetailsStrategy<?>> handlers) {

        this.strategies = List.copyOf(handlers);
    }

    public void save(Payment payment) {

        Objects.requireNonNull(payment, "Payment is required");

        PaymentDetailsStrategy<?> handler = strategies.stream()
                .filter(h -> h.isApplicable(payment))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No handler found for payment type: "
                                + payment.getClass().getSimpleName()
                ));

        handler.save(payment);
    }

    public Payment findById(PaymentRecord record) {

        Objects.requireNonNull(record, "Payment record is required");

        PaymentDetailsStrategy<?> strategy = strategies.stream()
                .filter(h -> h.supports(record))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No handler found for payment method: "
                                + record.paymentMethod()
                ));

        return strategy.findById(record);
    }
}