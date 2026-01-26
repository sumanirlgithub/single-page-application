package com.neo.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInsightResponse {

    private String paymentNumber;
    private String status; // e.g., "SUCCESS", "FAILED"
    private String transactionId; // optional, from gateway
    private String message;       // optional info or error details

    public PaymentInsightResponse(String paymentNumber, String status) {
    }
}
