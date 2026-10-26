
package com.example.sepa.processor.client;

import com.example.sepa.processor.dto.response.BankSubmissionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BankSubmissionClient {

    private final RestClient restClient;
    private final String submitPath;

    public BankSubmissionClient(
            RestClient.Builder builder,
            @Value("${bank.psp.base-url}") String baseUrl,
            @Value("${bank.psp.api-key}") String apiKey,
            @Value("${bank.psp.submit-path}") String submitPath) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + apiKey
                )
                .build();

        this.submitPath = submitPath;
    }

    public BankSubmissionResponse submit(
            String paymentId,
            String collectionId,
            String pain008Xml) {

        return restClient.post()
                .uri(submitPath)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON)
                // Use the same key when retrying the same payment,
                // if the provider supports idempotency keys.
                .header("Idempotency-Key", paymentId)
                .header("X-Collection-Id", collectionId)
                .body(pain008Xml)
                .retrieve()
                .body(BankSubmissionResponse.class);
    }
}