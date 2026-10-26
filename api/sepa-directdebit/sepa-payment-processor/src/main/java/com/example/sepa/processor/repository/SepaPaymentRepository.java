
package com.example.sepa.processor.repository;

import com.example.sepa.processor.domain.PaymentSubmissionData;
import com.example.sepa.processor.domain.SepaPayment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SepaPaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public SepaPaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<PaymentSubmissionData> findSubmissionData(
            String paymentId) {

        String sql = """
            SELECT PAYMENT_ID,
                   COLLECTION_ID,
                   PAIN008_XML,
                   SUBMISSION_STATUS
            FROM SEPA_PAYMENT
            WHERE PAYMENT_ID = ?
            """;

        List<PaymentSubmissionData> results = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new PaymentSubmissionData(
                        rs.getString("PAYMENT_ID"),
                        rs.getString("COLLECTION_ID"),
                        rs.getString("PAIN008_XML"),
                        rs.getString("SUBMISSION_STATUS")
                ),
                paymentId
        );

        return results.stream().findFirst();
    }

    public void updateSubmissionState(
            String paymentId,
            String paymentStatus,
            String submissionStatus,
            String providerReference) {

        String sql = """
            UPDATE SEPA_PAYMENT
            SET STATUS = ?,
                SUBMISSION_STATUS = ?,
                PROVIDER_REFERENCE = ?,
                UPDATED_AT = CURRENT_TIMESTAMP(6)
            WHERE PAYMENT_ID = ?
            """;

        int rowsUpdated = jdbcTemplate.update(
                sql,
                paymentStatus,
                submissionStatus,
                providerReference,
                paymentId
        );

        if (rowsUpdated != 1) {
            throw new IllegalStateException(
                    "Could not update submission state for paymentId="
                            + paymentId
            );
        }
    }

    public void save(SepaPayment payment) {

        String sql = """
                INSERT INTO SEPA_PAYMENT (
                    PAYMENT_ID,
                    COLLECTION_ID,
                    MANDATE_ID,
                    AMOUNT,
                    CURRENCY,
                    REQUESTED_COLLECTION_DATE,
                    STATUS,
                    PAIN008_XML,
                    SUBMISSION_STATUS,
                    PROVIDER_REFERENCE,
                    CREATED_AT,
                    UPDATED_AT
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                payment.paymentId(),
                payment.collectionId(),
                payment.mandateId(),
                payment.amount(),
                payment.currency(),
                payment.requestedCollectionDate(),
                payment.status(),
                payment.pain008Xml(),
                payment.submissionStatus(),
                payment.providerReference(),
                payment.createdAt(),
                payment.updatedAt()
        );
    }

    public boolean existsByCollectionId(String collectionId) {

        String sql = """
                SELECT COUNT(*)
                FROM SEPA_PAYMENT
                WHERE COLLECTION_ID = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                collectionId
        );

        return count != null && count > 0;
    }

    public void updateStatus(String paymentId, String status) {

        String sql = """
                UPDATE SEPA_PAYMENT
                SET STATUS = ?,
                    UPDATED_AT = CURRENT_TIMESTAMP(6)
                WHERE PAYMENT_ID = ?
                """;

        int rowsUpdated = jdbcTemplate.update(
                sql,
                status,
                paymentId
        );

        if (rowsUpdated != 1) {
            throw new IllegalStateException(
                    "Could not update status for paymentId=" + paymentId
            );
        }
    }

    /**
     * Saves the generated pain.008 XML and updates the payment status.
     */
    public void saveGeneratedXml(String paymentId, String xml) {

        if (xml == null || xml.isBlank()) {
            throw new IllegalArgumentException(
                    "pain.008 XML cannot be null or blank"
            );
        }

        String sql = """
                UPDATE SEPA_PAYMENT
                SET PAIN008_XML = ?,
                    STATUS = 'XML_GENERATED',
                    UPDATED_AT = CURRENT_TIMESTAMP(6)
                WHERE PAYMENT_ID = ?
                """;

        int rowsUpdated = jdbcTemplate.update(
                sql,
                xml,
                paymentId
        );

        if (rowsUpdated != 1) {
            throw new IllegalStateException(
                    "Could not save pain.008 XML for paymentId=" + paymentId
            );
        }
    }
}