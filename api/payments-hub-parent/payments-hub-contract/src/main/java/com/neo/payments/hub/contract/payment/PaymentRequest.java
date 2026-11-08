package com.neo.payments.hub.contract.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "paymentMethod",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = DirectDebitPaymentRequest.class,
                name = "DIRECT_DEBIT"
        ),
        @JsonSubTypes.Type(
                value = CardPaymentRequest.class,
                name = "CARD"
        )
})
public interface PaymentRequest {

    PaymentMethod paymentMethod();

    String submissionId();
}