package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.contract.submission.BankSubmissionRequestedEvent;
import com.neo.payments.hub.dao.submission.KafkaInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankSubmissionConsumer {

    private static final String EVENT_TYPE = "BANK_SUBMISSION_REQUESTED";

    private final KafkaInboxRepository kafkaInboxRepository;
    private final BankSubmissionService bankSubmissionService;

    @KafkaListener(
            topics = "${bank-submission.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Transactional(rollbackFor = Exception.class)
    public void consume(BankSubmissionRequestedEvent event) {

        log.info(
                "Received bank submission event. outboxId={}, paymentId={}",
                event.outboxId(),
                event.paymentId()
        );

        // Step 1: Check whether this event has already been processed.
        boolean firstProcessing =
                kafkaInboxRepository.saveIfNotProcessed(
                        event.outboxId(),
                        EVENT_TYPE
                );

        // Step 2: Ignore duplicate Kafka delivery.
        if (!firstProcessing) {
            log.info(
                    "Duplicate bank submission event ignored. outboxId={}",
                    event.outboxId()
            );
            return;
        }

        // Step 3: Perform database-local processing.
        // This method must NOT make the external bank HTTP call.
        bankSubmissionService.accept(event);

        log.info(
                "Bank submission event accepted successfully. outboxId={}, paymentId={}",
                event.outboxId(),
                event.paymentId()
        );
    }
}