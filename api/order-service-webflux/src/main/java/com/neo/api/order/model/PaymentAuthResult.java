package com.neo.api.order.model;

import java.time.Instant;

public record PaymentAuthResult(
        boolean approved,         // true if payment authorized
        String transactionId,     // payment transaction ID
        String reason,            // failure reason if declined
        Instant timestamp         // when the authorization occurred
) {
    public static PaymentAuthResult approved(String transactionId) {
        return new PaymentAuthResult(true, transactionId, null, Instant.now());
    }

    public static PaymentAuthResult declined(String reason) {
        return new PaymentAuthResult(false, null, reason, Instant.now());
    }
}
