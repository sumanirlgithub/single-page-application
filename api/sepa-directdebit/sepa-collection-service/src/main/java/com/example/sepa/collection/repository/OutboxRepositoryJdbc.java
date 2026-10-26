
package com.example.sepa.collection.repository;

import com.example.sepa.collection.domain.OutboxEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OutboxRepositoryJdbc implements OutboxRepository {

    private static final String INSERT_SQL = """
            INSERT INTO OUTBOX_EVENT (
                EVENT_ID,
                AGGREGATE_TYPE,
                AGGREGATE_ID,
                EVENT_TYPE,
                PAYLOAD,
                STATUS,
                CREATED_AT,
                RETRY_COUNT
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String FIND_UNPUBLISHED_SQL = """
            SELECT
                EVENT_ID,
                AGGREGATE_TYPE,
                AGGREGATE_ID,
                EVENT_TYPE,
                PAYLOAD,
                STATUS,
                CREATED_AT,
                RETRY_COUNT
            FROM OUTBOX_EVENT
            WHERE STATUS = 'PENDING'
            ORDER BY CREATED_AT ASC
            LIMIT ?
            """;

    private static final String MARK_AS_PUBLISHED_SQL = """
            UPDATE OUTBOX_EVENT
            SET STATUS = 'PUBLISHED',
                PUBLISHED_AT = CURRENT_TIMESTAMP(6)
            WHERE EVENT_ID = ?
              AND STATUS = 'PENDING'
            """;

    private static final String INCREMENT_RETRY_COUNT_SQL = """
            UPDATE OUTBOX_EVENT
            SET RETRY_COUNT = RETRY_COUNT + 1
            WHERE EVENT_ID = ?
              AND STATUS = 'PENDING'
            """;

    private final JdbcTemplate jdbcTemplate;

    public OutboxRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<OutboxEvent> rowMapper = new RowMapper<>() {
        @Override
        public OutboxEvent mapRow(ResultSet rs, int rowNum)
                throws SQLException {

            Timestamp createdAt = rs.getTimestamp("CREATED_AT");

            return new OutboxEvent(
                    rs.getString("EVENT_ID"),
                    rs.getString("AGGREGATE_TYPE"),
                    rs.getString("AGGREGATE_ID"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("PAYLOAD"),
                    rs.getString("STATUS"),
                    createdAt.toInstant(),
                    rs.getInt("RETRY_COUNT")
            );
        }
    };

    @Override
    public void save(OutboxEvent event) {
        jdbcTemplate.update(
                INSERT_SQL,
                event.eventId(),
                event.aggregateType(),
                event.aggregateId(),
                event.eventType(),
                event.payload(),
                event.status(),
                Timestamp.from(event.createdAt()),
                event.retryCount()
        );
    }

    @Override
    public List<OutboxEvent> findUnpublishedEvents(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "Limit must be greater than zero."
            );
        }

        return jdbcTemplate.query(
                FIND_UNPUBLISHED_SQL,
                rowMapper,
                limit
        );
    }

    @Override
    public void markAsPublished(String eventId) {
        jdbcTemplate.update(
                MARK_AS_PUBLISHED_SQL,
                eventId
        );
    }

    @Override
    public void incrementRetryCount(String eventId) {
        jdbcTemplate.update(
                INCREMENT_RETRY_COUNT_SQL,
                eventId
        );
    }
}