package com.neo.payments.hub.contract.submission;

public record BankSubmissionRequestedEvent(
        Long outboxId,
        String paymentId,
        String pain008Xml,
        String idempotencyKey
) {}