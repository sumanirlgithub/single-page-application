package com.neo.payments.hub.business.mapper.payment;

import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.model.payment.Payment;

import java.time.Instant;

public interface PaymentMapper {

    boolean isApplicable(PaymentRequest request);

    Payment toDomain(
            PaymentRequest request,
            String paymentId,
            String submissionId,
            Instant now
    );
}