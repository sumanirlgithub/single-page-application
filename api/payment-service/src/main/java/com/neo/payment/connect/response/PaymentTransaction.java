package com.neo.payment.connect.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransaction {
    private String uetr;
    private TransactionStatus transactionStatus;
    private PaymentEvent paymentEvent;
}
