package com.neo.payments.hub.dao.payment;

import com.neo.payments.hub.contract.payment.PaymentAction;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.PaymentRecord;
import com.neo.payments.hub.model.payment.PaymentStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(
            com.neo.payments.hub.model.payment.Payment payment) {

        String sql = """
                INSERT INTO PAYMENT (
                    PAYMENT_ID, SUBMISSION_ID, AMOUNT_REMIT,
                    CURRENCY, PAYMENT_DATE, PAYMENT_ACTION,
                    PAYMENT_METHOD, PAYMENT_STATUS,
                    CREATED_AT, UPDATED_AT, ID_INTERNAL
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                payment.getPaymentId(),
                payment.getSubmissionId(),
                payment.getAmountRemit(),
                payment.getCurrency(),
                payment.getPaymentDate(),
                payment.getPaymentAction() == null
                        ? null : payment.getPaymentAction().name(),
                payment.getPaymentMethod().name(),
                payment.getPaymentStatus().name(),
                Timestamp.from(payment.getCreatedAt()),
                Timestamp.from(payment.getUpdatedAt()),
                payment.getIdInternal()
        );
    }

    public Optional<PaymentRecord> findById(String paymentId) {

        String sql = """
                SELECT
                    PAYMENT_ID,
                    SUBMISSION_ID,
                    AMOUNT_REMIT,
                    CURRENCY,
                    PAYMENT_DATE,
                    PAYMENT_ACTION,
                    PAYMENT_METHOD,
                    PAYMENT_STATUS,
                    CREATED_AT,
                    UPDATED_AT,
                    ID_INTERNAL
                FROM PAYMENT
                WHERE PAYMENT_ID = ?
                """;

        return jdbcTemplate.query(
                sql,
                paymentRecordRowMapper(),
                paymentId
        ).stream().findFirst();
    }

    private RowMapper<PaymentRecord> paymentRecordRowMapper() {

        return (rs, rowNum) -> new PaymentRecord(
                rs.getString("PAYMENT_ID"),
                rs.getString("SUBMISSION_ID"),
                rs.getBigDecimal("AMOUNT_REMIT"),
                rs.getString("CURRENCY"),
                rs.getDate("PAYMENT_DATE").toLocalDate(),
                rs.getString("PAYMENT_ACTION") == null
                        ? null
                        : PaymentAction.valueOf(
                        rs.getString("PAYMENT_ACTION")),
                PaymentMethod.valueOf(
                        rs.getString("PAYMENT_METHOD")),
                PaymentStatus.valueOf(
                        rs.getString("PAYMENT_STATUS")),
                rs.getTimestamp("CREATED_AT").toInstant(),
                rs.getTimestamp("UPDATED_AT").toInstant(),
                rs.getObject("ID_INTERNAL", Integer.class)
        );
    }
}