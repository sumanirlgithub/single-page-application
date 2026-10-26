
package com.example.sepa.processor.domain;

public record DebtorResponse(
        String name,
        String iban,
        String bic
) {
}

