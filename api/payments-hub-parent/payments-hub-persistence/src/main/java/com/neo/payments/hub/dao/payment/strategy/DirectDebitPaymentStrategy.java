
package com.neo.payments.hub.dao.payment.strategy;

import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.DirectDebitPayment;
import com.neo.payments.hub.model.payment.PaymentRecord;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DirectDebitPaymentStrategy
        extends PaymentDetailsStrategy<DirectDebitPayment> {

    private final JdbcTemplate jdbcTemplate;

    public DirectDebitPaymentStrategy(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected Class<DirectDebitPayment> paymentClass() {
        return DirectDebitPayment.class;
    }

    @Override
    protected PaymentMethod paymentMethod() {
        return PaymentMethod.DIRECT_DEBIT;
    }

    @Override
    protected void saveDetails(DirectDebitPayment payment) {

        String sql = """
                INSERT INTO DIRECT_DEBIT_PAYMENT (
                    PAYMENT_ID,
                    DEBTOR_NAME,
                    DEBTOR_IBAN,
                    DEBTOR_BIC,
                    CREDITOR_NAME,
                    CREDITOR_ID,
                    CREDITOR_IBAN,
                    CREDITOR_BIC,
                    MANDATE_ID,
                    ID_UMR,
                    MANDATE_SIGNATURE_DATE,
                    SEQUENCE_TYPE
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                payment.getPaymentId(),
                payment.getDebtorName(),
                payment.getDebtorIban(),
                payment.getDebtorBic(),
                payment.getCreditorName(),
                payment.getCreditorId(),
                payment.getCreditorIban(),
                payment.getCreditorBic(),
                payment.getMandateId(),
                payment.getIdUmr(),
                payment.getMandateSignatureDate(),
                payment.getSequenceType()
        );
    }

    @Override
    protected DirectDebitPayment findDetails(PaymentRecord record) {

        String sql = """
                SELECT
                    DEBTOR_NAME,
                    DEBTOR_IBAN,
                    DEBTOR_BIC,
                    CREDITOR_NAME,
                    CREDITOR_ID,
                    CREDITOR_IBAN,
                    CREDITOR_BIC,
                    MANDATE_ID,
                    ID_UMR,
                    MANDATE_SIGNATURE_DATE,
                    SEQUENCE_TYPE
                FROM DIRECT_DEBIT_PAYMENT
                WHERE PAYMENT_ID = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new DirectDebitPayment(
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

                        rs.getString("DEBTOR_NAME"),
                        rs.getString("DEBTOR_IBAN"),
                        rs.getString("DEBTOR_BIC"),

                        rs.getString("CREDITOR_NAME"),
                        rs.getString("CREDITOR_ID"),
                        rs.getString("CREDITOR_IBAN"),
                        rs.getString("CREDITOR_BIC"),

                        rs.getString("MANDATE_ID"),
                        rs.getString("ID_UMR"),

                        rs.getDate("MANDATE_SIGNATURE_DATE").toLocalDate(),
                        rs.getString("SEQUENCE_TYPE")
                ),
                record.paymentId()
        );
    }
}
