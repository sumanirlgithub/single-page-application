
package com.neo.payments.hub.model.submission;

import java.time.Instant;

public record BankSubmission(
        Long submissionId,
        Long outboxId,
        String paymentId,
        String pain008Xml,
        String idempotencyKey,
        String submissionStatus,
        String providerReference,
        String responseMessage,
        Integer retryCount,
        Instant createdAt,
        Instant updatedAt
) {}
