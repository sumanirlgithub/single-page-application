package com.neo.payments.hub.business.strategy.bankaccount;

import com.neo.payments.hub.model.bankaccount.BankAccount;
import com.neo.payments.hub.model.web.Environment;

public interface BankAccountLookupStrategy {

    boolean isApplicable(Environment environment);

    BankAccount getBankAccount(String idUmr);
}