package com.example.mandate.domain;

import java.time.Instant;
import java.time.LocalDate;

public record SepaMandate(

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
// Mandate information
        String mandateType,
        LocalDate signatureDate,
        String status,
// Audit fields
        Instant createdAt,
        Instant updatedAt
) {
}