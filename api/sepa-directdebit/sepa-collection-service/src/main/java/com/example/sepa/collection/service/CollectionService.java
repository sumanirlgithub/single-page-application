package com.example.sepa.collection.service;

import com.example.sepa.collection.domain.DirectDebitCollection;
import com.example.sepa.collection.domain.DirectDebitCollectionRequestedEvent;
import com.example.sepa.collection.domain.OutboxEvent;
import com.example.sepa.collection.dto.request.CreateCollectionRequest;
import com.example.sepa.collection.dto.response.CollectionResponse;
import com.example.sepa.collection.mapper.CollectionMapper;
import com.example.sepa.collection.repository.CollectionRepository;
import com.example.sepa.collection.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final OutboxRepository outboxRepository;
    private final CollectionMapper collectionMapper;
    private final ObjectMapper objectMapper;

    public CollectionService(
            CollectionRepository collectionRepository,
            OutboxRepository outboxRepository,
            CollectionMapper collectionMapper,
            ObjectMapper objectMapper) {

        this.collectionRepository = collectionRepository;
        this.outboxRepository = outboxRepository;
        this.collectionMapper = collectionMapper;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public CollectionResponse createCollection(
            CreateCollectionRequest request) {

        validateRequest(request);

        Instant now = Instant.now();

        String collectionId = generateCollectionId();
        String eventId = UUID.randomUUID().toString();

        // 1. Create the collection domain object.
        DirectDebitCollection collection =
                collectionMapper.toDomain(
                        request,
                        collectionId,
                        now
                );

        // 2. Create the event to be published to Kafka.
        DirectDebitCollectionRequestedEvent event =
                new DirectDebitCollectionRequestedEvent(
                        eventId,
                        "DirectDebitCollectionRequested",
                        1,
                        now,
                        collection.collectionId(),
                        collection.mandateId(),
                        collection.amount(),
                        collection.currency(),
                        collection.requestedCollectionDate(),
                        collection.sequenceType()
                );

        // 3. Serialize the event to JSON for the outbox.
        String payload = toJson(event);

        OutboxEvent outboxEvent = new OutboxEvent(
                eventId,
                "DIRECT_DEBIT_COLLECTION",
                collectionId,
                event.eventType(),
                payload,
                "PENDING",
                now,
                0 //re-try count start at 0
        );

        // 4. Save both records within the same DB transaction.
        collectionRepository.save(collection);
        outboxRepository.save(outboxEvent);

        // 5. Return the collection response.
        // Kafka publishing happens asynchronously via the outbox publisher.
        return collectionMapper.toResponse(collection);
    }

    private void validateRequest(CreateCollectionRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Collection request is required."
            );
        }

        if (request.requestedCollectionDate() != null
                && request.requestedCollectionDate()
                .isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Requested collection date cannot be in the past."
            );
        }

        if (request.sequenceType() != null) {
            String sequenceType = request.sequenceType()
                    .trim()
                    .toUpperCase(Locale.ROOT);

            if (!sequenceType.matches("FRST|RCUR|OOFF|FNAL")) {
                throw new IllegalArgumentException(
                        "Unsupported sequence type."
                );
            }
        }

        if (request.currency() != null
                && !request.currency().trim()
                .matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException(
                    "Currency must be a three-letter code."
            );
        }
    }

    private String generateCollectionId() {
        return "DD-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .toUpperCase(Locale.ROOT);
    }

    private String toJson(
            DirectDebitCollectionRequestedEvent event) {

        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(
                    "Unable to serialize collection event.",
                    exception
            );
        }
    }
}