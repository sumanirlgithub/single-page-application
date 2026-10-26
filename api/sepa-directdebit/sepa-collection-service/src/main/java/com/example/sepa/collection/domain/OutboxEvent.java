package com.example.sepa.collection.domain;

import java.time.Instant;

public record OutboxEvent(
        String eventId,
        String aggregateType,
        String aggregateId,
        String eventType,
        String payload,
        String status,
        Instant createdAt,
        int retryCount
) {}