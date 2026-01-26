package com.neo.api.order.events;

import java.io.Serializable;
import java.time.Instant;

public record PaymentAuthorizationPendingEvent(
        String orderId,        // The order waiting for payment
        Instant timestamp      // When the event was published
) implements Serializable {

    public static PaymentAuthorizationPendingEvent of(String orderId) {
        return new PaymentAuthorizationPendingEvent(orderId, Instant.now());
    }
}
