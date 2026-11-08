package com.neo.payments.hub.contract.submission;

public record BankSubmissionResponse(
        boolean accepted,
        String providerReference,
        String responseMessage
) {
}