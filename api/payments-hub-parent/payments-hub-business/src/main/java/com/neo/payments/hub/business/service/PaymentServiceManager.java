package com.neo.payments.hub.business.service;

import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.Payment;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentServiceManager {

    private final List<PaymentService> paymentMethodServices;

    public PaymentServiceManager(List<PaymentService> paymentMethodServices) {

        this.paymentMethodServices = paymentMethodServices;
    }

    public PaymentService getService(PaymentMethod paymentMethod) {

        return paymentMethodServices.stream()
                .filter(service -> service.isApplicable(paymentMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No payment service found for: "
                                + paymentMethod.name()
                ));
    }
}