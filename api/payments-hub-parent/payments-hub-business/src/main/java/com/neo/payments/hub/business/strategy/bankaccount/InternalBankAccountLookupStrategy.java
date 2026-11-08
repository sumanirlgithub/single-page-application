package com.neo.payments.hub.business.strategy.bankaccount;

import com.neo.payments.hub.model.bankaccount.BankAccount;
import com.neo.payments.hub.model.web.Environment;

public class InternalBankAccountLookupStrategy implements BankAccountLookupStrategy {

    @Override
    public boolean isApplicable(Environment environment) {
        return Environment.ROS.equals(environment);
    }

    @Override
    public BankAccount getBankAccount(String idUmr) {
        return null;
    }
}
