package com.neo.payments.hub.model.payment;

import com.neo.payments.hub.contract.payment.PaymentAction;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
public final class CardPayment extends Payment {

    private final String cardToken;
    private final String merchantId;
    private final String authorizationReference;

    public CardPayment(
            String paymentId,
            String submissionId,
            BigDecimal amountRemit,
            String currency,
            LocalDate paymentDate,
            Integer idInternal,
            PaymentAction paymentAction,
            PaymentStatus paymentStatus,
            Instant createdAt,
            Instant updatedAt,
            String cardToken,
            String merchantId,
            String authorizationReference
    ) {

        super(
                paymentId,
                submissionId,
                amountRemit,
                currency,
                paymentDate,
                idInternal,
                paymentAction,
                PaymentMethod.CARD,
                paymentStatus,
                createdAt,
                updatedAt
        );

        this.cardToken = requireValue(cardToken, "Card token");
        this.merchantId = requireValue(merchantId, "Merchant ID");
        this.authorizationReference = authorizationReference;
    }


    private static String requireValue(String value, String field) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " is required"
            );
        }

        return value;
    }
}
