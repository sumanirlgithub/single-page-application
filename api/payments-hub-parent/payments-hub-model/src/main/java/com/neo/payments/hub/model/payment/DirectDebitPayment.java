package com.neo.payments.hub.model.payment;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.neo.payments.hub.contract.payment.PaymentAction;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.bankaccount.BankAccount;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@JsonPropertyOrder({
        "idInternal",
        "paymentId",
        "idUmr",
        "mandateId",
        "submissionId",
        "paymentMethod",
        "amountRemit",
        "currency",
        "paymentDate",
        "paymentAction",
        "paymentStatus",
        "createdAt",
        "updatedAt",
        "debtorName",
        "debtorIban",
        "debtorBic",
        "creditorName",
        "creditorId",
        "creditorIban",
        "creditorBic",
        "mandateSignatureDate",
        "sequenceType"
})
@Getter
public final class DirectDebitPayment extends Payment {

    // Debtor information
    private final String debtorName;
    private final String debtorIban;
    private final String debtorBic;

    // Creditor information
    private final String creditorName;
    private final String creditorId;
    private final String creditorIban;
    private final String creditorBic;

    // Mandate information
    private final String mandateId;
    private final String idUmr;
    private final LocalDate mandateSignatureDate;
    private final String sequenceType;

    public DirectDebitPayment(
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
            String debtorName,
            String debtorIban,
            String debtorBic,
            String creditorName,
            String creditorId,
            String creditorIban,
            String creditorBic,
            String mandateId,
            String idUmr,
            LocalDate mandateSignatureDate,
            String sequenceType
    ) {

        super(
                paymentId,
                submissionId,
                amountRemit,
                currency,
                paymentDate,
                idInternal,
                paymentAction,
                PaymentMethod.DIRECT_DEBIT,
                paymentStatus,
                createdAt,
                updatedAt
        );

        this.debtorName = requireValue(debtorName, "Debtor name");
        this.debtorIban = requireValue(debtorIban, "Debtor IBAN");
        this.debtorBic = debtorBic;

        this.creditorName = requireValue(creditorName, "Creditor name");
        this.creditorId = requireValue(creditorId, "Creditor ID");
        this.creditorIban = requireValue(creditorIban, "Creditor IBAN");
        this.creditorBic = creditorBic;

        this.mandateId = requireValue(mandateId, "Mandate ID");
        this.idUmr = requireValue(idUmr, "Mandate reference");

        this.mandateSignatureDate = Objects.requireNonNull(
                mandateSignatureDate,
                "Mandate signature date is required"
        );

        this.sequenceType = requireValue(sequenceType, "Sequence type");
    }

    private static String requireValue(String value, String field) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }

        return value;
    }

    public DirectDebitPayment withBankAccount(BankAccount bankAccount) {

        Objects.requireNonNull(
                bankAccount,
                "Bank account is required"
        );

        return new DirectDebitPayment(
                getPaymentId(),
                getSubmissionId(),
                getAmountRemit(),
                getCurrency(),
                getPaymentDate(),
                getIdInternal(),
                getPaymentAction(),
                getPaymentStatus(),
                getCreatedAt(),
                getUpdatedAt(),

                // Replace debtor account with mandate bank account
                bankAccount.accountHolderName(),
                bankAccount.iban(),
                bankAccount.bic(),

                // Keep creditor details unchanged
                creditorName,
                creditorId,
                creditorIban,
                creditorBic,

                // Keep mandate details unchanged
                mandateId,
                idUmr,
                mandateSignatureDate,
                sequenceType
        );
    }
}