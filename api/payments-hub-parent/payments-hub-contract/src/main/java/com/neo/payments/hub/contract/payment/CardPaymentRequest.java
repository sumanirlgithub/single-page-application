package com.neo.payments.hub.contract.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardPaymentRequest(
        Integer idInternal,
        String idUmr,
        BigDecimal amountRemit,
        String currency,
        LocalDate paymentDate,
        PaymentAction paymentAction,

        String cardToken,
        String merchantId,
        String authorizationReference
) implements PaymentRequest {
    @Override
    public PaymentMethod paymentMethod() {
        return null;
    }

    @Override
    public String submissionId() {
        return "";
    }
}