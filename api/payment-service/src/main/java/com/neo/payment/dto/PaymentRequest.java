package com.neo.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String orderId;
    private String customerId;
    private double amount;
    private String currency;
    private String paymentMethod; // e.g., "CREDIT_CARD", "PAYPAL"
}
