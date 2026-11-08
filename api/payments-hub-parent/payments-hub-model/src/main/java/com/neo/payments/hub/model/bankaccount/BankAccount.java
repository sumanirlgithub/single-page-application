package com.neo.payments.hub.model.bankaccount;


public record BankAccount(
        String accountHolderName,
        String iban,
        String bic
) {
}