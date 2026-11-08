package com.neo.payments.hub.business.service.integration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CustomerServiceClient {

    private final RestClient restClient;

    public CustomerServiceClient(
            @Qualifier("customerRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String getCustomer(String idEntity) {
        return restClient.get()
                .uri("/api/customer/{idEntity}", idEntity)
                .retrieve()
                .body(String.class);
    }
}

