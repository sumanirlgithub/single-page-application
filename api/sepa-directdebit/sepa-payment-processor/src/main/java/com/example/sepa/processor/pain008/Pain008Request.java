package com.example.sepa.processor.pain008;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Pain008Request(

        // Message information
        String messageId,
        LocalDate requestedCollectionDate,

        // Creditor
        String creditorName,
        String creditorId,
        String creditorIban,
        String creditorBic,

        // Debtor
        String debtorName,
        String debtorIban,
        String debtorBic,

        // Mandate
        String mandateId,
        LocalDate mandateSignatureDate,

        // Payment
        BigDecimal amount,
        String currency,

        // Sequence
        String sequenceType,

        // Internal reference
        String collectionId

) {
}