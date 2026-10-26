
package com.example.sepa.processor.domain;

public record PaymentSubmissionData(
        String paymentId,
        String collectionId,
        String pain008Xml,
        String submissionStatus
) {}