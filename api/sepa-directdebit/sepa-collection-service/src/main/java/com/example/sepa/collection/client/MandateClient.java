package com.example.sepa.collection.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MandateClient {

    private final RestClient restClient;

    public MandateClient(
            RestClient.Builder restClientBuilder) {

        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public MandateResponse getMandate(String mandateId) {

        return restClient
                .get()
                .uri("/api/v1/mandates/{mandateId}", mandateId)
                .retrieve()
                .body(MandateResponse.class);
    }

    public record MandateResponse(
            String mandateId,
            String mandateType,
            String debtorName,
            String debtorIban,
            String debtorBic,
            String creditorId,
            String creditorName,
            String signatureDate,
            String status
    ) {
    }
}