package com.neo.payments.hub.business.mapper.payment;

import com.neo.payments.hub.contract.payment.PaymentRequest;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMapperRegistry {

    private final List<PaymentMapper> paymentMappers;

    public PaymentMapperRegistry(List<PaymentMapper> paymentMappers) {
        this.paymentMappers = paymentMappers;
    }

    public PaymentMapper getMapper(PaymentRequest request) {

        return paymentMappers.stream()
                .filter(mapper -> mapper.isApplicable(request))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No mapper found for payment request: "
                                + request.getClass().getSimpleName()
                ));
    }
}