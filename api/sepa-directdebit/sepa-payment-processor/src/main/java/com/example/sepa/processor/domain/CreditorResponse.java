package com.example.sepa.processor.domain;

public record CreditorResponse(
        String name,
        String creditorId,
        String iban,
        String bic
) {
}

