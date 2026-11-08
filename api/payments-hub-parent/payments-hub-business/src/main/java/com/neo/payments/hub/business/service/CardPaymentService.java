package com.neo.payments.hub.business.service;

import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.CardPayment;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.web.Environment;
import org.springframework.stereotype.Service;

@Service
public class CardPaymentService implements PaymentService {

    @Override
    public boolean isApplicable(PaymentMethod paymentMethod) {

        return PaymentMethod.CARD.equals(paymentMethod);
    }

    @Override
    public void processPayment(Payment payment, Environment environment) {
        // Card specific business logic goes here.
    }
}