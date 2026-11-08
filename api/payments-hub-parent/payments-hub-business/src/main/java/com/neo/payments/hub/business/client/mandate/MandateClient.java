package com.neo.payments.hub.business.client.mandate;

import com.neo.payments.hub.contract.bankaccount.MandateBankAccountResponse;
import com.neo.payments.hub.model.mandate.MandateResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MandateClient {

    private final RestClient restClient;

    public MandateClient(
            @Qualifier("mandateRestClient") RestClient restClient) {

        this.restClient = restClient;
    }

    public MandateResponse getBankAccount(String idUmr) {
        return restClient.get()
                .uri("/api/v1/mandates/{idUmr}", idUmr)
                .retrieve()
                .body(MandateResponse.class);
    }
}