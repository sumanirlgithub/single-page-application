
package com.neo.payments.hub.dao.payment.strategy;

import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.CardPayment;
import com.neo.payments.hub.model.payment.PaymentRecord;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CardPaymentStrategy
        extends PaymentDetailsStrategy<CardPayment> {

    private final JdbcTemplate jdbcTemplate;

    public CardPaymentStrategy(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected Class<CardPayment> paymentClass() {
        return CardPayment.class;
    }

    @Override
    protected PaymentMethod paymentMethod() {
        return PaymentMethod.CARD;
    }

    @Override
    protected void saveDetails(CardPayment payment) {

        String sql = """
                INSERT INTO CARD_PAYMENT (
                    PAYMENT_ID,
                    CARD_TOKEN,
                    MERCHANT_ID,
                    AUTHORIZATION_REFERENCE
                )
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                payment.getPaymentId(),
                payment.getCardToken(),
                payment.getMerchantId(),
                payment.getAuthorizationReference()
        );
    }

    @Override
    protected CardPayment findDetails(PaymentRecord record) {

        String sql = """
                SELECT
                    CARD_TOKEN,
                    MERCHANT_ID,
                    AUTHORIZATION_REFERENCE
                FROM CARD_PAYMENT
                WHERE PAYMENT_ID = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new CardPayment(
                        record.paymentId(),
                        record.submissionId(),
                        record.amountRemit(),
                        record.currency(),
                        record.paymentDate(),
                        record.idInternal(),
                        record.paymentAction(),
                        record.paymentStatus(),
                        record.createdAt(),
                        record.updatedAt(),
                        rs.getString("CARD_TOKEN"),
                        rs.getString("MERCHANT_ID"),
                        rs.getString("AUTHORIZATION_REFERENCE")
                ),
                record.paymentId()
        );
    }
}
