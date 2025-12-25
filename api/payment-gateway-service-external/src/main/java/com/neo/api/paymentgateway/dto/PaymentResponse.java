package com.neo.api.paymentgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String orderId;
    private String status; // e.g., "SUCCESS", "FAILED"
    private String transactionId; // optional, from gateway
    private String message;       // optional info or error details
}
