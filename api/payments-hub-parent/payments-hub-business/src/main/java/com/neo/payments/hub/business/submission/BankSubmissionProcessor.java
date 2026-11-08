package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.contract.submission.BankSubmissionResponse;
import com.neo.payments.hub.dao.submission.BankSubmissionRepository;
import com.neo.payments.hub.model.submission.BankSubmission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankSubmissionProcessor {

    private final BankSubmissionClient bankSubmissionClient;
    private final BankSubmissionRepository bankSubmissionRepository;

    public void submit(BankSubmission submission) {

        try {
            BankSubmissionResponse response =
                    bankSubmissionClient.submit(
                            submission.paymentId(),
                            submission.pain008Xml(),
                            submission.idempotencyKey()
                    );

            log.info(
                    "Bank simulator response received. paymentId={}, accepted={}, providerReference={}",
                    submission.paymentId(),
                    response.accepted(),
                    response.providerReference()
            );

            bankSubmissionRepository.updateSubmissionResult(
                    submission.submissionId(),
                    response.accepted() ? "ACCEPTED" : "REJECTED",
                    response.providerReference(),
                    response.responseMessage()
            );

            log.info(
                    "Bank submission status updated. submissionId={}, status={}",
                    submission.submissionId(),
                    response.accepted() ? "ACCEPTED" : "REJECTED"
            );

        } catch (Exception ex) {
            log.error(
                    "Bank submission failed. paymentId={}",
                    submission.paymentId(),
                    ex
            );

            // A timeout can mean the bank accepted the payment
            // but the response was lost. Do not assume rejection.
            bankSubmissionRepository.updateSubmissionResult(
                    submission.submissionId(),
                    "UNKNOWN",
                    null,
                    ex.getMessage()
            );
        }
    }
}