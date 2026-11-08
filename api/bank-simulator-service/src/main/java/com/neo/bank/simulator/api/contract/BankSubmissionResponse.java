package com.neo.bank.simulator.api.contract;

public record BankSubmissionResponse(
        boolean accepted,
        String providerReference,
        String responseMessage
) {
}