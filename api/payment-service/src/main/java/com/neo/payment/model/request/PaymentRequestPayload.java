package com.neo.payment.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestPayload {

    private String orderId;
    private String customerId;
    private Double amount;
}
