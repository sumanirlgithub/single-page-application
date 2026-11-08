
package com.neo.payments.hub.dao.submission;

import com.neo.payments.hub.model.submission.BankSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BankSubmissionRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<BankSubmission> findPendingSubmissions(int limit) {

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "Limit must be greater than zero"
            );
        }

        String sql = """
        SELECT
            SUBMISSION_ID,
            OUTBOX_ID,
            PAYMENT_ID,
            PAIN008_XML,
            IDEMPOTENCY_KEY,
            SUBMISSION_STATUS,
            PROVIDER_REFERENCE,
            RESPONSE_MESSAGE,
            RETRY_COUNT,
            CREATED_AT,
            UPDATED_AT
        FROM BANK_SUBMISSION
        WHERE SUBMISSION_STATUS = 'PENDING'
        ORDER BY SUBMISSION_ID
        LIMIT ?
        """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new BankSubmission(
                        rs.getLong("SUBMISSION_ID"),
                        rs.getLong("OUTBOX_ID"),
                        rs.getString("PAYMENT_ID"),
                        rs.getString("PAIN008_XML"),
                        rs.getString("IDEMPOTENCY_KEY"),
                        rs.getString("SUBMISSION_STATUS"),
                        rs.getString("PROVIDER_REFERENCE"),
                        rs.getString("RESPONSE_MESSAGE"),
                        rs.getInt("RETRY_COUNT"),
                        rs.getTimestamp("CREATED_AT").toInstant(),
                        rs.getTimestamp("UPDATED_AT").toInstant()
                ),
                limit
        );
    }

    public BankSubmission save(BankSubmission submission) {

        String sql = """
                INSERT INTO BANK_SUBMISSION (
                    OUTBOX_ID,
                    PAYMENT_ID,
                    PAIN008_XML,
                    IDEMPOTENCY_KEY,
                    SUBMISSION_STATUS,
                    PROVIDER_REFERENCE,
                    RESPONSE_MESSAGE,
                    RETRY_COUNT,
                    CREATED_AT,
                    UPDATED_AT
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setLong(1, submission.outboxId());
            ps.setString(2, submission.paymentId());
            ps.setString(3, submission.pain008Xml());
            ps.setString(4, submission.idempotencyKey());
            ps.setString(5, submission.submissionStatus());
            ps.setString(6, submission.providerReference());
            ps.setString(7, submission.responseMessage());
            ps.setInt(8, submission.retryCount());
            ps.setTimestamp(9, Timestamp.from(submission.createdAt()));
            ps.setTimestamp(10, Timestamp.from(submission.updatedAt()));

            return ps;
        }, keyHolder);

        return new BankSubmission(
                keyHolder.getKey().longValue(),
                submission.outboxId(),
                submission.paymentId(),
                submission.pain008Xml(),
                submission.idempotencyKey(),
                submission.submissionStatus(),
                submission.providerReference(),
                submission.responseMessage(),
                submission.retryCount(),
                submission.createdAt(),
                submission.updatedAt()
        );
    }

    public void updateSubmissionResult(
            Long submissionId,
            String submissionStatus,
            String providerReference,
            String responseMessage) {

        String sql = """
        UPDATE BANK_SUBMISSION
        SET
            SUBMISSION_STATUS = ?,
            PROVIDER_REFERENCE = ?,
            RESPONSE_MESSAGE = ?,
            UPDATED_AT = CURRENT_TIMESTAMP
        WHERE SUBMISSION_ID = ?
        """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                submissionStatus,
                providerReference,
                responseMessage,
                submissionId
        );

        if (rowsAffected == 0) {
            throw new IllegalStateException(
                    "Bank submission not found. submissionId="
                            + submissionId
            );
        }
    }


}
