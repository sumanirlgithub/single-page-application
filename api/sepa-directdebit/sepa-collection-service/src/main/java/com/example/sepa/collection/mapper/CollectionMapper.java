package com.example.sepa.collection.mapper;

import com.example.sepa.collection.domain.DirectDebitCollection;
import com.example.sepa.collection.dto.request.CreateCollectionRequest;
import com.example.sepa.collection.dto.response.CollectionResponse;

import java.time.Instant;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class CollectionMapper {

    public DirectDebitCollection toDomain(
            CreateCollectionRequest request,
            String collectionId,
            Instant createdAt) {

        return new DirectDebitCollection(
                collectionId,
                request.mandateId().trim(),
                request.amount(),
                request.currency().trim().toUpperCase(Locale.ROOT),
                request.requestedCollectionDate(),
                request.sequenceType().trim().toUpperCase(Locale.ROOT),
                "RECEIVED",
                createdAt
        );
    }

    public CollectionResponse toResponse(
            DirectDebitCollection collection) {

        return new CollectionResponse(
                collection.collectionId(),
                collection.mandateId(),
                collection.amount(),
                collection.currency(),
                collection.requestedCollectionDate(),
                collection.sequenceType(),
                collection.status(),
                collection.createdAt()
        );
    }
}