
package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.contract.submission.BankSubmissionRequestedEvent;
import com.neo.payments.hub.dao.submission.BankSubmissionRepository;
import com.neo.payments.hub.model.submission.BankSubmission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankSubmissionService {

    private final BankSubmissionRepository bankSubmissionRepository;

    public void accept(BankSubmissionRequestedEvent event) {

        if (event == null) {
            throw new IllegalArgumentException(
                    "Bank submission event cannot be null"
            );
        }

        if (event.outboxId() == null || event.outboxId() <= 0) {
            throw new IllegalArgumentException(
                    "Outbox ID must be greater than zero"
            );
        }

        if (event.paymentId() == null || event.paymentId().isBlank()) {
            throw new IllegalArgumentException(
                    "Payment ID is required"
            );
        }

        if (event.pain008Xml() == null || event.pain008Xml().isBlank()) {
            throw new IllegalArgumentException(
                    "PAIN.008 XML is required"
            );
        }

        if (event.idempotencyKey() == null
                || event.idempotencyKey().isBlank()) {
            throw new IllegalArgumentException(
                    "Idempotency key is required"
            );
        }

        Instant now = Instant.now();

        BankSubmission submission = new BankSubmission(
                null,
                event.outboxId(),
                event.paymentId(),
                event.pain008Xml(),
                event.idempotencyKey(),
                "PENDING",
                null,
                null,
                0,
                now,
                now
        );

        bankSubmissionRepository.save(submission);

        log.info(
                "Bank submission request accepted. outboxId={}, paymentId={}",
                event.outboxId(),
                event.paymentId()
        );
    }
}
