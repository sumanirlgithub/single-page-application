package com.neo.payments.hub.model.payment;

public record PaymentProcessingResult(
        Payment payment,
        String generatedDocument
) {
}