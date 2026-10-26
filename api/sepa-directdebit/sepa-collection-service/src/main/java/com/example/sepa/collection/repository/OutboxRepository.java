package com.example.sepa.collection.repository;

import com.example.sepa.collection.domain.OutboxEvent;

import java.util.List;

public interface OutboxRepository {

    void save(OutboxEvent event);

    List<OutboxEvent> findUnpublishedEvents(int limit);

    void markAsPublished(String eventId);

    void incrementRetryCount(String eventId);
}