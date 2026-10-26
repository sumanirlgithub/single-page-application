package com.example.mandate.dto.response;

public record CreditorResponse(
        String name,
        String creditorId,
        String iban,
        String bic
) {
}

