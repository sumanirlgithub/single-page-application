package com.example.mandate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

@Repository
public class IdempotencyRepository {

    private final JdbcTemplate jdbcTemplate;

    public IdempotencyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insertIfAbsent(
            String idempotencyKey,
            String operationName,
            String requestHash) {

        String sql = """
                INSERT IGNORE INTO API_IDEMPOTENCY (
                    IDEMPOTENCY_KEY,
                    OPERATION_NAME,
                    REQUEST_HASH,
                    STATUS,
                    CREATED_AT,
                    UPDATED_AT
                )
                VALUES (?, ?, ?, 'PROCESSING', ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                idempotencyKey,
                operationName,
                requestHash,
                Instant.now(),
                Instant.now()
        );

        return rows == 1;
    }

    public Optional<IdempotencyRecord> findByKey(
            String idempotencyKey,
            String operationName) {

        String sql = """
                SELECT IDEMPOTENCY_KEY,
                       OPERATION_NAME,
                       REQUEST_HASH,
                       RESOURCE_ID,
                       STATUS,
                       RESPONSE_STATUS,
                       RESPONSE_BODY
                FROM API_IDEMPOTENCY
                WHERE IDEMPOTENCY_KEY = ?
                  AND OPERATION_NAME = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs),
                idempotencyKey,
                operationName
        ).stream().findFirst();
    }

    public void markCompleted(
            String idempotencyKey,
            String operationName,
            String resourceId,
            int responseStatus,
            String responseBody) {

        String sql = """
                UPDATE API_IDEMPOTENCY
                SET RESOURCE_ID = ?,
                    STATUS = 'COMPLETED',
                    RESPONSE_STATUS = ?,
                    RESPONSE_BODY = ?,
                    UPDATED_AT = ?
                WHERE IDEMPOTENCY_KEY = ?
                  AND OPERATION_NAME = ?
                  AND STATUS = 'PROCESSING'
                """;

        int rows = jdbcTemplate.update(
                sql,
                resourceId,
                responseStatus,
                responseBody,
                Instant.now(),
                idempotencyKey,
                operationName
        );

        if (rows != 1) {
            throw new IllegalStateException(
                    "Unable to complete idempotency record"
            );
        }
    }

    private IdempotencyRecord mapRow(ResultSet rs)
            throws SQLException {

        return new IdempotencyRecord(
                rs.getString("IDEMPOTENCY_KEY"),
                rs.getString("OPERATION_NAME"),
                rs.getString("REQUEST_HASH"),
                rs.getString("RESOURCE_ID"),
                rs.getString("STATUS"),
                (Integer) rs.getObject("RESPONSE_STATUS"),
                rs.getString("RESPONSE_BODY")
        );
    }

    public record IdempotencyRecord(
            String idempotencyKey,
            String operationName,
            String requestHash,
            String resourceId,
            String status,
            Integer responseStatus,
            String responseBody
    ) {}
}