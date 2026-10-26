package com.example.sepa.collection.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record CreateDirectDebitResponse(

        String collectionId,

        String mandateId,

        BigDecimal amount,

        String currency,

        LocalDate requestedCollectionDate,

        String status,

        Instant createdAt
) {
}