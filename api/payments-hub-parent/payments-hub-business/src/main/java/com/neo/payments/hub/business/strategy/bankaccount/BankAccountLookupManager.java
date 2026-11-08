package com.neo.payments.hub.business.strategy.bankaccount;


import com.neo.payments.hub.model.web.Environment;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This manager does not know anything about RestClient, Mandate Service URLs or IBANs.
 * It only selects the correct strategy.
 */
@Component
public class BankAccountLookupManager {

    private final List<BankAccountLookupStrategy> strategies;

    public BankAccountLookupManager(
            List<BankAccountLookupStrategy> strategies) {

        this.strategies = strategies;
    }

    public BankAccountLookupStrategy getStrategy(Environment environment) {

        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(environment))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No bank account lookup strategy found for environment: "
                                + environment
                ));
    }
}