package com.neo.payments.hub.business.mapper.payment;

import com.neo.payments.hub.contract.payment.CardPaymentRequest;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.model.payment.CardPayment;
import com.neo.payments.hub.model.payment.Payment;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CardPaymentMapper implements PaymentMapper {

    @Override
    public boolean isApplicable(PaymentRequest request) {
        return request instanceof CardPaymentRequest;
    }

    @Override
    public Payment toDomain(
            PaymentRequest request,
            String paymentId,
            String submissionId,
            Instant now) {

        CardPaymentRequest cardRequest = (CardPaymentRequest) request;

        // Map CardPaymentRequest to CardPayment
        return null;
    }
}