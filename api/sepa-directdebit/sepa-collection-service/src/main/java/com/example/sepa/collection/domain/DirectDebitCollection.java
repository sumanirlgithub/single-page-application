package com.example.sepa.collection.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DirectDebitCollection(
        String collectionId,
        String mandateId,
        BigDecimal amount,
        String currency,
        LocalDate requestedCollectionDate,
        String sequenceType,
        String status,
        Instant createdAt
) {}