package com.neo.payments.hub.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neo.payments.hub.contract.payment.PaymentAction;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "paymentMethod"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = DirectDebitPayment.class,
                name = "DIRECT_DEBIT"
        ),
        @JsonSubTypes.Type(
                value = CardPayment.class,
                name = "CARD"
        )
})
@Getter
public abstract class Payment {

    private final String paymentId;
    private final String submissionId;
    private final BigDecimal amountRemit;
    private final String currency;
    private final LocalDate paymentDate;
    private  final PaymentAction paymentAction;
    private final PaymentMethod paymentMethod;
    private final PaymentStatus paymentStatus;
    private final Instant createdAt;
    private final Instant updatedAt;

    /** Customer number **/
    private final Integer idInternal;

    protected Payment(
            String paymentId,
            String submissionId,
            BigDecimal amountRemit,
            String currency,
            LocalDate paymentDate,
            Integer idInternal,
            PaymentAction paymentAction,
            PaymentMethod paymentMethod,
            PaymentStatus paymentStatus,
            Instant createdAt,
            Instant updatedAt
    ) {

        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID is required");
        }

        this.paymentId = paymentId;
        this.submissionId = submissionId;

        Objects.requireNonNull(amountRemit, "Payment amount is required");

        if (amountRemit.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero");
        }

        this.amountRemit = amountRemit;

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        this.currency = currency.toUpperCase(Locale.ROOT);

        this.paymentDate = Objects.requireNonNull(
                paymentDate, "Payment date is required");

        this.paymentAction = paymentAction;

        this.paymentMethod = Objects.requireNonNull(
                paymentMethod, "Payment method is required");

        this.paymentStatus = Objects.requireNonNull(
                paymentStatus, "Payment status is required");

        this.createdAt = Objects.requireNonNull(
                createdAt, "Created timestamp is required");

        this.updatedAt = Objects.requireNonNull(
                updatedAt, "Updated timestamp is required");

        this.idInternal = idInternal;
    }

    @JsonIgnore
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

}