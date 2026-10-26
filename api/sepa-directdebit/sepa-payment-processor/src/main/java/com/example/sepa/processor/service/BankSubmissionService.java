
package com.example.sepa.processor.service;

import com.example.sepa.processor.client.BankSubmissionClient;
import com.example.sepa.processor.dto.response.BankSubmissionResponse;
import com.example.sepa.processor.domain.PaymentSubmissionData;
import com.example.sepa.processor.repository.SepaPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Service
@Slf4j
public class BankSubmissionService {

    private final SepaPaymentRepository paymentRepository;
    private final BankSubmissionClient bankSubmissionClient;

    public BankSubmissionService(
            SepaPaymentRepository paymentRepository,
            BankSubmissionClient bankSubmissionClient) {
        this.paymentRepository = paymentRepository;
        this.bankSubmissionClient = bankSubmissionClient;
    }

    public void submit(String paymentId) {

        PaymentSubmissionData payment = paymentRepository
                .findSubmissionData(paymentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Payment not found: " + paymentId
                ));

        if (payment.pain008Xml() == null
                || payment.pain008Xml().isBlank()) {
            throw new IllegalStateException(
                    "pain.008 XML has not been generated for paymentId="
                            + paymentId
            );
        }

        String currentState = payment.submissionStatus();

        if ("SUBMITTED".equalsIgnoreCase(currentState)
                || "ACCEPTED".equalsIgnoreCase(currentState)) {
            log.info(
                    "Payment has already been submitted. paymentId={}",
                    paymentId
            );
            return;
        }

        if ("SUBMITTING".equalsIgnoreCase(currentState)
                || "SUBMISSION_UNKNOWN".equalsIgnoreCase(currentState)) {
            throw new IllegalStateException(
                    "Submission outcome requires reconciliation before retry. "
                            + "paymentId=" + paymentId
            );
        }

        if ("REJECTED".equalsIgnoreCase(currentState)) {
            throw new IllegalStateException(
                    "Payment was rejected. Investigate before resubmitting. "
                            + "paymentId=" + paymentId
            );
        }

        // Persist the attempt before calling the external provider.
        // If the process crashes during the HTTP call, SUBMITTING
        // prevents an automatic blind resubmission.
        paymentRepository.updateSubmissionState(
                paymentId,
                "SUBMITTING",
                "SUBMITTING",
                null
        );

        try {
            BankSubmissionResponse response =
                    bankSubmissionClient.submit(
                            payment.paymentId(),
                            payment.collectionId(),
                            payment.pain008Xml()
                    );

            if (response != null
                    && "REJECTED".equalsIgnoreCase(response.status())) {

                paymentRepository.updateSubmissionState(
                        paymentId,
                        "SUBMISSION_REJECTED",
                        "REJECTED",
                        response.providerReference()
                );

                log.warn(
                        "Provider rejected payment submission. paymentId={}",
                        paymentId
                );
                return;
            }

            // A successful HTTP response means the submission request
            // was received according to this example contract. It does
            // not mean the direct debit has been collected successfully.
            String providerReference = response == null
                    ? null
                    : response.providerReference();

            paymentRepository.updateSubmissionState(
                    paymentId,
                    "SUBMITTED",
                    "SUBMITTED",
                    providerReference
            );

            log.info(
                    "Payment submitted to provider. paymentId={}, reference={}",
                    paymentId,
                    providerReference
            );

        } catch (HttpClientErrorException ex) {

            int statusCode = ex.getStatusCode().value();

            // A timeout or rate-limit response may not tell us
            // conclusively whether the provider processed the request.
            if (statusCode == 408 || statusCode == 429) {
                markOutcomeUnknown(paymentId);
                return;
            }

            // Adapt this mapping if the provider uses other 4xx
            // responses for transient or ambiguous outcomes.
            paymentRepository.updateSubmissionState(
                    paymentId,
                    "SUBMISSION_REJECTED",
                    "REJECTED",
                    null
            );

            log.warn(
                    "Provider returned HTTP {} for paymentId={}",
                    statusCode,
                    paymentId
            );

        } catch (RestClientException ex) {

            // A network failure or server error can occur after the
            // provider has received the request. Do not blindly retry.
            markOutcomeUnknown(paymentId);

            log.error(
                    "Could not confirm provider submission outcome. "
                            + "paymentId={}",
                    paymentId
            );
        }
    }

    private void markOutcomeUnknown(String paymentId) {
        paymentRepository.updateSubmissionState(
                paymentId,
                "SUBMISSION_UNKNOWN",
                "SUBMISSION_UNKNOWN",
                null
        );
    }
}