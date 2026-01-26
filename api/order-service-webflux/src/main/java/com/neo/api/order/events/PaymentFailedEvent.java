package com.neo.api.order.events;

import java.io.Serializable;
import java.time.Instant;

public record PaymentFailedEvent(
        String orderId,         // The order that failed payment
        String reason,          // Failure reason (e.g., "CARD_DECLINED", "INSUFFICIENT_FUNDS")
        String paymentMethod,   // Optional: credit card, PayPal, etc.
        Instant timestamp       // When the failure occurred
) implements Serializable {

    public static PaymentFailedEvent of(String orderId, String reason, String paymentMethod) {
        return new PaymentFailedEvent(orderId, reason, paymentMethod, Instant.now());
    }
}
