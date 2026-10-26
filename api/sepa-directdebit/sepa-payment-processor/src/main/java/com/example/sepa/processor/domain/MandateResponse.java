package com.example.sepa.processor.domain;

import java.time.Instant;
import java.time.LocalDate;

public record MandateResponse(

        String mandateId,

        String status,

        DebtorResponse debtor,

        CreditorResponse creditor,

        String mandateType,

        LocalDate signatureDate,

        Instant createdAt,

        Instant updatedAt
) {
}

