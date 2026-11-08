package com.neo.payments.hub.model.mandate;

import java.time.Instant;
import java.time.LocalDate;

public record MandateResponse(

        String mandateId,

        String idUmr,

        String status,

        DebtorResponse debtor,

        CreditorResponse creditor,

        String mandateType,

        LocalDate signatureDate,

        Instant createdAt,

        Instant updatedAt
) {
}

