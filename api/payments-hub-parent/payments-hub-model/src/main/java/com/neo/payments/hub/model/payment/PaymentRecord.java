package com.neo.payments.hub.model.payment;


import com.neo.payments.hub.contract.payment.PaymentAction;
import com.neo.payments.hub.contract.payment.PaymentMethod;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PaymentRecord(
        String paymentId,
        String submissionId,
        BigDecimal amountRemit,
        String currency,
        LocalDate paymentDate,
        PaymentAction paymentAction,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        Instant createdAt,
        Instant updatedAt,
        Integer idInternal
) {
}