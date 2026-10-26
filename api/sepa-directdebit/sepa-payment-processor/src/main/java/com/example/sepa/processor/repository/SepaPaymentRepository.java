package com.example.sepa.processor.repository;

import com.example.sepa.processor.domain.SepaPayment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SepaPaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public SepaPaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                    CREATED_AT,
                    UPDATED_AT
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
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

    public void updateStatus(
            String paymentId,
            String status) {

        String sql = """
                UPDATE SEPA_PAYMENT
                SET STATUS = ?,
                    UPDATED_AT = CURRENT_TIMESTAMP
                WHERE PAYMENT_ID = ?
                """;

        jdbcTemplate.update(
                sql,
                status,
                paymentId
        );
    }
}