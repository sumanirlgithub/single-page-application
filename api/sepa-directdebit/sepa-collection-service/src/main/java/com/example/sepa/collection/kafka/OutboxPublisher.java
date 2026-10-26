package com.example.sepa.collection.kafka;

import com.example.sepa.collection.domain.OutboxEvent;
import com.example.sepa.collection.repository.OutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OutboxPublisher {

    private static final String TOPIC = "DIRECT-DEBIT-CREATED";

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(
            OutboxRepository outboxRepository,
            KafkaTemplate<String, String> kafkaTemplate) {

        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishOutboxEvents() {

        List<OutboxEvent> events =
                outboxRepository.findUnpublishedEvents(100);

        for (OutboxEvent event : events) {

            try {

                kafkaTemplate.send(
                        TOPIC,
                        event.aggregateId(),
                        event.payload()
                ).get(10, TimeUnit.SECONDS);

                // Kafka confirmed successful publishing
                outboxRepository.markAsPublished(event.eventId());

            } catch (Exception e) {

                // Kafka failed - leave event unpublished
                outboxRepository.incrementRetryCount(event.eventId());

                System.err.println(
                        "Failed to publish outbox event: "
                                + event.eventId()
                                + ", error: "
                                + e.getMessage()
                );
            }
        }
    }
}