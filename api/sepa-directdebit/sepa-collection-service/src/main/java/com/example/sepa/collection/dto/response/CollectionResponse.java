package com.example.sepa.collection.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record CollectionResponse(
        String collectionId,
        String mandateId,
        BigDecimal amount,
        String currency,
        LocalDate requestedCollectionDate,
        String sequenceType,
        String status,
        Instant createdAt
) {}