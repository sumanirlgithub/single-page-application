
package com.example.sepa.processor.dto.response;

public record BankSubmissionResponse(
        String status,
        String providerReference,
        String message
) {}