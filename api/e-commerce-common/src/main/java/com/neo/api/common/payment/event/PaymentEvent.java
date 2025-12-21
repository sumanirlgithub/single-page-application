package com.neo.api.common.payment.event;

import com.neo.api.common.enums.PaymentEventName;

import java.io.Serializable;
import java.time.Instant;

public record PaymentEvent(PaymentEventName eventName, String orderId, String paymentStatus, Instant createdDate) implements Serializable {

}