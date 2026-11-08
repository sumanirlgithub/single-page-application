package com.neo.payments.hub.model.submission;

import java.time.Instant;

public record BankSubmissionOutboxEvent(
        Long outboxId,
        String paymentId,
        String eventType,
        String eventStatus,
        Integer retryCount,
        String pain008Xml,
        Instant createdAt,
        Instant publishedAt
) {}
