
package com.example.sepa.collection.repository;

import com.example.sepa.collection.domain.DirectDebitCollection;

import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CollectionRepositoryJdbc implements CollectionRepository {

    private static final String INSERT_SQL = """
            INSERT INTO DIRECT_DEBIT_COLLECTION (
                COLLECTION_ID,
                MANDATE_ID,
                AMOUNT,
                CURRENCY,
                REQUESTED_COLLECTION_DATE,
                SEQUENCE_TYPE,
                STATUS,
                CREATED_AT
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    public CollectionRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(DirectDebitCollection collection) {

        jdbcTemplate.update(
                INSERT_SQL,
                collection.collectionId(),
                collection.mandateId(),
                collection.amount(),
                collection.currency(),
                collection.requestedCollectionDate(),
                collection.sequenceType(),
                collection.status(),
                Timestamp.from(collection.createdAt())
        );
    }
}