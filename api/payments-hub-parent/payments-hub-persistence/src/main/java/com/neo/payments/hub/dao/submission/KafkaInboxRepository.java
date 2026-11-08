
package com.neo.payments.hub.dao.submission;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KafkaInboxRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Records an event if it has not already been processed.
     *
     * @return true  - event is being processed for the first time
     *         false - event has already been processed
     */
    public boolean saveIfNotProcessed(
            Long eventId,
            String eventType) {

        if (eventId == null || eventId <= 0) {
            throw new IllegalArgumentException(
                    "Event ID must be greater than zero"
            );
        }

        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException(
                    "Event type is required"
            );
        }

        String sql = """
                INSERT IGNORE INTO KAFKA_INBOX (
                    EVENT_ID,
                    EVENT_TYPE,
                    PROCESSED_AT
                )
                VALUES (?, ?, CURRENT_TIMESTAMP)
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                eventId,
                eventType
        );

        return rowsAffected == 1;
    }
}
