package com.neo.payments.hub.business.processor;

import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentProcessingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentProcessingResult process(Payment payment) {
        log.info("AbstractPaymentProcessor -> process() method");
        return null;
    }

    //@Override
    //public Payment updatePaymentAmount(PaymentRequest) {
    //}
}
