package com.neo.payments.hub.contract.bankaccount;

public record MandateBankAccountResponse(
        String idUmr,
        String accountHolderName,
        String iban,
        String bic
) {
}