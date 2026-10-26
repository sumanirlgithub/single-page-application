package com.example.sepa.processor.client;


import com.example.sepa.processor.domain.MandateResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MandateClient {

    private final RestClient restClient;

    public MandateClient(RestClient mandateRestClient) {
        this.restClient = mandateRestClient;
    }

    public MandateResponse getMandate(String mandateId) {

        return restClient
                .get()
                .uri("/api/v1/mandates/{mandateId}", mandateId)
                .retrieve()
                .body(MandateResponse.class);
    }
}