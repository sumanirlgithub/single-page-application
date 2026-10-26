package com.example.sepa.processor.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DirectDebitCreatedEvent(

        String collectionId,

        String mandateId,

        // Debtor
        String debtorName,
        String debtorIban,
        String debtorBic,

        // Creditor
        String creditorName,
        String creditorId,
        String creditorIban,
        String creditorBic,

        BigDecimal amount,
        String currency,

        LocalDate requestedCollectionDate,

        // SEPA Direct Debit information
        String sequenceType

) {
}