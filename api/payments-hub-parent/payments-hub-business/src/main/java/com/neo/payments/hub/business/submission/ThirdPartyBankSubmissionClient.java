package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.contract.submission.BankSubmissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ThirdPartyBankSubmissionClient
        implements BankSubmissionClient {

    private final RestClient bankRestClient;

    @Value("${bank-submission.api.submit-path}")
    private String submitPath;

    @Override
    public BankSubmissionResponse submit(
            String paymentId,
            String pain008Xml,
            String idempotencyKey) {

        return bankRestClient.post()
                .uri(submitPath)
                .header("Idempotency-Key", idempotencyKey)
                .header("Content-Type", "application/xml")
                .body(pain008Xml)
                .retrieve()
                .body(BankSubmissionResponse.class);
    }
}