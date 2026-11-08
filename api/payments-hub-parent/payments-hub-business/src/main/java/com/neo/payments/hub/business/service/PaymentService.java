package com.neo.payments.hub.business.service;

import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentProcessingResult;
import com.neo.payments.hub.model.web.Environment;

public interface PaymentService {

    boolean isApplicable(PaymentMethod paymentMethod);

    void processPayment(Payment payment, Environment environment);
}