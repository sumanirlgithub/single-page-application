package com.neo.api.paymentgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInsightResponse {

    private UUID paymentNumber;
    private String status; // e.g., "SUCCESS", "FAILED"
    private String transactionId; // optional, from gateway
    private String message;       // optional info or error details
}
