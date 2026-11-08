package com.neo.payments.hub.business.processor;

import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentProcessingResult;
import com.neo.payments.hub.model.web.Environment;

import java.sql.SQLException;

public interface PaymentProcessor {

    boolean isApplicable(Environment environment);

    PaymentProcessingResult process(Payment payment);

    //Payment updatePaymentAmount(PaymentRequest paymentRequest);
}
