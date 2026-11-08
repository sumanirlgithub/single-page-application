package com.neo.payments.hub.business.strategy.bankaccount;

import com.neo.payments.hub.business.client.mandate.MandateClient;

import com.neo.payments.hub.contract.bankaccount.MandateBankAccountResponse;
import com.neo.payments.hub.model.bankaccount.BankAccount;
import com.neo.payments.hub.model.mandate.MandateResponse;
import com.neo.payments.hub.model.web.Environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThirdPartyBankAccountLookupStrategy implements BankAccountLookupStrategy {

    private final MandateClient mandateClient;

    public ThirdPartyBankAccountLookupStrategy(MandateClient mandateClient) {
        this.mandateClient = mandateClient;
    }

    @Override
    public boolean isApplicable(Environment environment) {
        return Environment.THIRD_PARTY.equals(environment);
    }

    @Override
    public BankAccount getBankAccount(String idUmr) {

        MandateResponse response =
                mandateClient.getBankAccount(idUmr);

        return new BankAccount(
                response.debtor().name(),
                response.debtor().iban(),
                response.debtor().bic()
        );
    }
}