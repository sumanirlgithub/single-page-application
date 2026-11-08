
package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.dao.submission.BankSubmissionRepository;
import com.neo.payments.hub.model.submission.BankSubmission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankSubmissionWorker {

    private final BankSubmissionRepository bankSubmissionRepository;
    private final BankSubmissionProcessor bankSubmissionProcessor;

    @Value("${bank-submission.worker.batch-size:100}")
    private int batchSize;

    @Scheduled(
            fixedDelayString =
                    "${bank-submission.worker.poll-interval:5000}"
    )
    public void processPendingSubmissions() {

        List<BankSubmission> submissions =
                bankSubmissionRepository.findPendingSubmissions(batchSize);

        if (submissions.isEmpty()) {
            return;
        }

        log.debug(
                "Found {} pending bank submissions",
                submissions.size()
        );

        for (BankSubmission submission : submissions) {

            log.info(
                    "Processing bank submission. " +
                            "submissionId={}, outboxId={}, paymentId={}, status={}",
                    submission.submissionId(),
                    submission.outboxId(),
                    submission.paymentId(),
                    submission.submissionStatus()
            );

            try {

                bankSubmissionProcessor.submit(submission);

            } catch (Exception ex) {

                log.error(
                        "Unexpected error processing bank submission. " +
                                "submissionId={}, paymentId={}",
                        submission.submissionId(),
                        submission.paymentId(),
                        ex
                );
            }
        }
    }
}
