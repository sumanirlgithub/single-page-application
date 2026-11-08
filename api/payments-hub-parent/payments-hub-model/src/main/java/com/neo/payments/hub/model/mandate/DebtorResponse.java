
package com.neo.payments.hub.model.mandate;

public record DebtorResponse(
        String name,
        String iban,
        String bic
) {
}

