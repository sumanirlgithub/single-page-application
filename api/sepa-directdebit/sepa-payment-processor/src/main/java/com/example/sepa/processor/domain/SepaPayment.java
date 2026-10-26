
package com.example.sepa.processor.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record SepaPayment(
        String paymentId,
        String collectionId,
        String mandateId,
        BigDecimal amount,
        String currency,
        LocalDate requestedCollectionDate,
        String status,
        String pain008Xml,
        String submissionStatus,
        String providerReference,
        Instant createdAt,
        Instant updatedAt
) {
}