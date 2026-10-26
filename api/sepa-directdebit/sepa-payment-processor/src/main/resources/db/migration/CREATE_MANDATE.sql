CREATE TABLE SEPA_MANDATE (
    MANDATE_ID           VARCHAR2(50) PRIMARY KEY,
    MANDATE_TYPE         VARCHAR2(20) NOT NULL,
    DEBTOR_NAME          VARCHAR2(200) NOT NULL,
    DEBTOR_IBAN         VARCHAR2(34) NOT NULL,
    DEBTOR_BIC          VARCHAR2(11),
    CREDITOR_ID          VARCHAR2(35) NOT NULL,
    CREDITOR_NAME        VARCHAR2(200) NOT NULL,
    SIGNATURE_DATE       DATE NOT NULL,
    STATUS               VARCHAR2(20) NOT NULL,
    CREATED_AT           TIMESTAMP NOT NULL,
    UPDATED_AT           TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX UK_SEPA_MANDATE_IBAN
ON SEPA_MANDATE (MANDATE_ID);



-- For a production implementation, I would normally also consider fields such as:

--   amendment_indicator
--   previous_mandate_id
--   sequence_type
--   electronic_mandate_reference
--   scheme
--   country_code
--   source_system
--   version