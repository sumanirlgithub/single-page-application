package com.neo.payments.hub.model.mandate;

public record CreditorResponse(
        String name,
        String creditorId,
        String iban,
        String bic
) {
}

