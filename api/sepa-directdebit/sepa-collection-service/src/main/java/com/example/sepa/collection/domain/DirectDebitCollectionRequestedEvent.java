package com.example.sepa.collection.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DirectDebitCollectionRequestedEvent(
        String eventId,
        String eventType,
        int eventVersion,
        Instant occurredAt,
        String collectionId,
        String mandateId,
        BigDecimal amount,
        String currency,
        LocalDate requestedCollectionDate,
        String sequenceType
) {}