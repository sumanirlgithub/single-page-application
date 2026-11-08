package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.contract.submission.BankSubmissionRequestedEvent;
import com.neo.payments.hub.dao.submission.BankSubmissionOutboxRepository;
import com.neo.payments.hub.model.submission.BankSubmissionOutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankSubmissionOutboxPublisher {

    private final BankSubmissionOutboxRepository outboxRepository;

    private final KafkaTemplate<String, BankSubmissionRequestedEvent>
            kafkaTemplate;

    @Value("${bank-submission.kafka.topic}")
    private String topic;

    @Value("${bank-submission.outbox.batch-size:100}")
    private int batchSize;

    @Scheduled(
            fixedDelayString = "${bank-submission.outbox.poll-interval:5000}"
    )
    public void publishPendingEvents() {

        List<BankSubmissionOutboxEvent> events =
                outboxRepository.findUnpublishedEvents(batchSize);

        if (events.isEmpty()) {
            return;
        }

        log.info("Found {} pending bank submission events", events.size());

        for (BankSubmissionOutboxEvent outboxEvent : events) {
            publishEvent(outboxEvent);
        }
    }

    private void publishEvent(BankSubmissionOutboxEvent outboxEvent) {

        try {
            BankSubmissionRequestedEvent event =
                    new BankSubmissionRequestedEvent(
                            outboxEvent.outboxId(),
                            outboxEvent.paymentId(),
                            outboxEvent.pain008Xml(),
                            outboxEvent.paymentId()
                    );

            kafkaTemplate.send(
                    topic,
                    outboxEvent.paymentId(),
                    event
            ).get(30, TimeUnit.SECONDS);

            outboxRepository.markAsPublished(outboxEvent.outboxId());

            log.info(
                    "Bank submission event published successfully. outboxId={}, paymentId={}",
                    outboxEvent.outboxId(),
                    outboxEvent.paymentId()
            );

        } catch (Exception ex) {

            outboxRepository.incrementRetryCount(outboxEvent.outboxId());

            log.error(
                    "Failed to publish bank submission event. outboxId={}, paymentId={}",
                    outboxEvent.outboxId(),
                    outboxEvent.paymentId(),
                    ex
            );
        }
    }
}