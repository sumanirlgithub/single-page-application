package com.example.mandate.repository;

import com.example.mandate.domain.SepaMandate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class MandateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MandateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Persists a new mandate.
     */
    public void save(SepaMandate mandate) {

        String sql = """
                INSERT INTO MANDATE (
                    MANDATE_ID,
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
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                mandate.mandateId(),
                mandate.debtorName(),
                mandate.debtorIban(),
                mandate.debtorBic(),
                mandate.creditorName(),
                mandate.creditorId(),
                mandate.creditorIban(),
                mandate.creditorBic(),
                mandate.mandateType(),
                Date.valueOf(mandate.signatureDate()),
                mandate.status(),
                Timestamp.from(mandate.createdAt()),
                Timestamp.from(mandate.updatedAt())
        );
    }

    /**
     * Finds a mandate by its unique mandate ID.
     */
    public Optional<SepaMandate> findById(String mandateId) {

        String sql = """
                SELECT
                    MANDATE_ID,
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

        List<SepaMandate> mandates = jdbcTemplate.query(
                sql,
                this::mapRow,
                mandateId
        );

        return mandates.stream().findFirst();
    }

    /**
     * Checks whether an active mandate already exists
     * for the given debtor and creditor.
     */
    public boolean existsActiveMandate(
            String debtorIban,
            String creditorId) {

        String sql = """
                SELECT COUNT(*)
                FROM MANDATE
                WHERE DEBTOR_IBAN = ?
                  AND CREDITOR_ID = ?
                  AND STATUS = 'ACTIVE'
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                debtorIban,
                creditorId
        );

        return count != null && count > 0;
    }

    /**
     * Updates the status of an existing mandate.
     */
    public int updateStatus(
            String mandateId,
            String status) {

        String sql = """
                UPDATE MANDATE
                SET STATUS = ?,
                    UPDATED_AT = CURRENT_TIMESTAMP
                WHERE MANDATE_ID = ?
                """;

        return jdbcTemplate.update(
                sql,
                status,
                mandateId
        );
    }

    /**
     * Maps one database row to the Mandate domain object.
     */
    private SepaMandate mapRow(
            java.sql.ResultSet rs,
            int rowNum) throws java.sql.SQLException {

        return new SepaMandate(
                rs.getString("MANDATE_ID"),

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

