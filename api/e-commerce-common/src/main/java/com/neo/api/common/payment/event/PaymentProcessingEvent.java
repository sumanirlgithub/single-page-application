package com.neo.api.common.payment.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentProcessingEvent implements Serializable {

    public static final String PAYMENT_PROCESSING_TOPIC = "PAYMENT-PROCESSING-TOPIC";

    private String orderId;
    private Integer cardNumber;
    private String cardHolder;
    private BigDecimal amount;
    private String cardExpiry;
    private Integer cvc;

}
