package com.neo.api.order.events;

import java.io.Serializable;
import java.time.Instant;

public record PaymentAuthorizedEvent(
        String orderId,            // The order being paid
        String transactionId,      // Payment transaction ID
        String paymentMethod,      // Optional, e.g., CREDIT_CARD, PAYPAL
        Instant timestamp          // When payment was authorized
) implements Serializable {

    public static PaymentAuthorizedEvent of(String orderId, String transactionId, String paymentMethod) {
        return new PaymentAuthorizedEvent(orderId, transactionId, paymentMethod, Instant.now());
    }
}
