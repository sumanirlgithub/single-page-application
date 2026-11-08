package com.neo.payments.hub.model.payment;

import java.time.Instant;

public record PaymentDocument(
        Long documentId,
        String paymentId,
        String documentType,
        String documentContent,
        String documentStatus,
        Instant createdAt
) {
}