
package com.example.mandate.repository;

import com.example.mandate.domain.SepaMandate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class MandateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MandateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(SepaMandate mandate) {

        String sql = """
                INSERT INTO MANDATE (
                    MANDATE_ID,
                    MANDATE_REFERENCE,
                    DEBTOR_NAME,
                    DEBTOR_IBAN,
                    DEBTOR_BIC,
                    CREDITOR_NAME,
                    CREDITOR_ID,
                    CREDITOR_IBAN,
                    CREDITOR_BIC,
                    MANDATE_TYPE,
                    SIGNATURE_DATE,
                    STATUS,
                    CREATED_AT,
                    UPDATED_AT
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                mandate.mandateId(),
                mandate.mandateReference(),
                mandate.debtorName(),
                mandate.debtorIban(),
                mandate.debtorBic(),
                mandate.creditorName(),
                mandate.creditorId(),
                mandate.creditorIban(),
                mandate.creditorBic(),
                mandate.mandateType(),
                mandate.signatureDate(),
                mandate.status(),
                mandate.createdAt(),
                mandate.updatedAt()
        );
    }

    public Optional<SepaMandate> findById(String mandateId) {

        String sql = """
                SELECT
                    MANDATE_ID,
                    MANDATE_REFERENCE,
                    DEBTOR_NAME,
                    DEBTOR_IBAN,
                    DEBTOR_BIC,
                    CREDITOR_NAME,
                    CREDITOR_ID,
                    CREDITOR_IBAN,
                    CREDITOR_BIC,
                    MANDATE_TYPE,
                    SIGNATURE_DATE,
                    STATUS,
                    CREATED_AT,
                    UPDATED_AT
                FROM MANDATE
                WHERE MANDATE_ID = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRowToMandate(rs),
                mandateId
        ).stream().findFirst();
    }

    private SepaMandate mapRowToMandate(ResultSet rs)
            throws SQLException {

        return new SepaMandate(
                rs.getString("MANDATE_ID"),
                rs.getString("MANDATE_REFERENCE"),
                rs.getString("DEBTOR_NAME"),
                rs.getString("DEBTOR_IBAN"),
                rs.getString("DEBTOR_BIC"),
                rs.getString("CREDITOR_NAME"),
                rs.getString("CREDITOR_ID"),
                rs.getString("CREDITOR_IBAN"),
                rs.getString("CREDITOR_BIC"),
                rs.getString("MANDATE_TYPE"),
                rs.getDate("SIGNATURE_DATE").toLocalDate(),
                rs.getString("STATUS"),
                rs.getTimestamp("CREATED_AT").toInstant(),
                rs.getTimestamp("UPDATED_AT").toInstant()
        );
    }
}
