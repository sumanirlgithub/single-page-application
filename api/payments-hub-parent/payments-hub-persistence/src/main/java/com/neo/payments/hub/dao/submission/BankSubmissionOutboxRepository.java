
package com.neo.payments.hub.dao.submission;

import com.neo.payments.hub.model.submission.BankSubmissionOutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankSubmissionOutboxRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Saves a new bank submission outbox event.
     */
    public BankSubmissionOutboxEvent save(
            BankSubmissionOutboxEvent event) {

        String sql = """
                INSERT INTO BANK_SUBMISSION_OUTBOX (
                    PAYMENT_ID,
                    EVENT_TYPE,
                    EVENT_STATUS,
                    RETRY_COUNT,
                    PAIN008_XML,
                    CREATED_AT,
                    PUBLISHED_AT
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, event.paymentId());
            ps.setString(2, event.eventType());
            ps.setString(3, event.eventStatus());
            ps.setInt(4, event.retryCount());
            ps.setString(5, event.pain008Xml());
            ps.setTimestamp(6, Timestamp.from(event.createdAt()));

            if (event.publishedAt() != null) {
                ps.setTimestamp(
                        7,
                        Timestamp.from(event.publishedAt())
                );
            } else {
                ps.setTimestamp(7, null);
            }

            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return new BankSubmissionOutboxEvent(
                generatedId,
                event.paymentId(),
                event.eventType(),
                event.eventStatus(),
                event.retryCount(),
                event.pain008Xml(),
                event.createdAt(),
                event.publishedAt()
        );
    }

    /**
     * Retrieves pending events for Kafka publication.
     */
    public List<BankSubmissionOutboxEvent> findUnpublishedEvents(
            int limit) {

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "Batch size must be greater than zero"
            );
        }

        String sql = """
                SELECT OUTBOX_ID,
                       PAYMENT_ID,
                       EVENT_TYPE,
                       EVENT_STATUS,
                       RETRY_COUNT,
                       PAIN008_XML,
                       CREATED_AT,
                       PUBLISHED_AT
                FROM BANK_SUBMISSION_OUTBOX
                WHERE EVENT_STATUS = 'PENDING'
                ORDER BY OUTBOX_ID
                LIMIT ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new BankSubmissionOutboxEvent(
                        rs.getLong("OUTBOX_ID"),
                        rs.getString("PAYMENT_ID"),
                        rs.getString("EVENT_TYPE"),
                        rs.getString("EVENT_STATUS"),
                        rs.getInt("RETRY_COUNT"),
                        rs.getString("PAIN008_XML"),
                        rs.getTimestamp("CREATED_AT").toInstant(),
                        rs.getTimestamp("PUBLISHED_AT") != null
                                ? rs.getTimestamp("PUBLISHED_AT").toInstant()
                                : null
                ),
                limit
        );
    }

    /**
     * Marks an event as successfully published to Kafka.
     */
    public int markAsPublished(Long outboxId) {

        String sql = """
                UPDATE BANK_SUBMISSION_OUTBOX
                SET EVENT_STATUS = 'PUBLISHED',
                    PUBLISHED_AT = ?
                WHERE OUTBOX_ID = ?
                  AND EVENT_STATUS = 'PENDING'
                """;

        return jdbcTemplate.update(
                sql,
                Timestamp.from(Instant.now()),
                outboxId
        );
    }

    /**
     * Increments retry count after a publication failure.
     */
    public int incrementRetryCount(Long outboxId) {

        String sql = """
                UPDATE BANK_SUBMISSION_OUTBOX
                SET RETRY_COUNT = RETRY_COUNT + 1
                WHERE OUTBOX_ID = ?
                  AND EVENT_STATUS = 'PENDING'
                """;

        return jdbcTemplate.update(sql, outboxId);
    }

    /**
     * Retrieves an outbox event by its ID.
     */
    public Optional<BankSubmissionOutboxEvent> findById(
            Long outboxId) {

        String sql = """
                SELECT OUTBOX_ID,
                       PAYMENT_ID,
                       EVENT_TYPE,
                       EVENT_STATUS,
                       RETRY_COUNT,
                       PAIN008_XML,
                       CREATED_AT,
                       PUBLISHED_AT
                FROM BANK_SUBMISSION_OUTBOX
                WHERE OUTBOX_ID = ?
                """;

        List<BankSubmissionOutboxEvent> events = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new BankSubmissionOutboxEvent(
                        rs.getLong("OUTBOX_ID"),
                        rs.getString("PAYMENT_ID"),
                        rs.getString("EVENT_TYPE"),
                        rs.getString("EVENT_STATUS"),
                        rs.getInt("RETRY_COUNT"),
                        rs.getString("PAIN008_XML"),
                        rs.getTimestamp("CREATED_AT").toInstant(),
                        rs.getTimestamp("PUBLISHED_AT") != null
                                ? rs.getTimestamp("PUBLISHED_AT").toInstant()
                                : null
                ),
                outboxId
        );

        return events.stream().findFirst();
    }
}
