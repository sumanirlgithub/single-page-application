package com.example.sepa.processor.pain008;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class Pain008Validator {

    private static final Set<String> VALID_SEQUENCE_TYPES =
            Set.of("FRST", "RCUR", "OOFF", "FNAL");

    public void validate(Pain008Request request) {

        validateRequiredFields(request);

        validateAmount(request);

        validateCurrency(request);

        validateIbans(request);

        validateBics(request);

        validateSequenceType(request);
    }

    private void validateRequiredFields(
            Pain008Request request) {

        if (isBlank(request.messageId())) {
            throw new IllegalArgumentException(
                    "Message ID is required");
        }

        if (request.requestedCollectionDate() == null) {
            throw new IllegalArgumentException(
                    "Requested collection date is required");
        }

        if (isBlank(request.creditorName())) {
            throw new IllegalArgumentException(
                    "Creditor name is required");
        }

        if (isBlank(request.creditorId())) {
            throw new IllegalArgumentException(
                    "Creditor ID is required");
        }

        if (isBlank(request.creditorIban())) {
            throw new IllegalArgumentException(
                    "Creditor IBAN is required");
        }

        if (isBlank(request.debtorName())) {
            throw new IllegalArgumentException(
                    "Debtor name is required");
        }

        if (isBlank(request.debtorIban())) {
            throw new IllegalArgumentException(
                    "Debtor IBAN is required");
        }

        if (isBlank(request.mandateId())) {
            throw new IllegalArgumentException(
                    "Mandate ID is required");
        }

        if (request.mandateSignatureDate() == null) {
            throw new IllegalArgumentException(
                    "Mandate signature date is required");
        }

        if (isBlank(request.sequenceType())) {
            throw new IllegalArgumentException(
                    "Sequence type is required");
        }

        if (isBlank(request.collectionId())) {
            throw new IllegalArgumentException(
                    "Collection ID is required");
        }
    }

    private void validateAmount(
            Pain008Request request) {

        BigDecimal amount = request.amount();

        if (amount == null) {
            throw new IllegalArgumentException(
                    "Amount is required");
        }

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        if (amount.scale() > 2) {
            throw new IllegalArgumentException(
                    "Amount cannot contain more than 2 decimal places");
        }
    }

    private void validateCurrency(
            Pain008Request request) {

        if (!"EUR".equalsIgnoreCase(
                request.currency())) {

            throw new IllegalArgumentException(
                    "SEPA Direct Debit currency must be EUR");
        }
    }

    private void validateIbans(
            Pain008Request request) {

        validateIban(
                request.creditorIban(),
                "Creditor IBAN");

        validateIban(
                request.debtorIban(),
                "Debtor IBAN");
    }

    private void validateIban(
            String iban,
            String fieldName) {

        String normalized =
                iban.replace(" ", "")
                        .toUpperCase();

        if (!normalized.matches(
                "^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$")) {

            throw new IllegalArgumentException(
                    fieldName + " has invalid format");
        }
    }

    private void validateBics(
            Pain008Request request) {

        if (!isBlank(request.creditorBic())) {

            validateBic(
                    request.creditorBic(),
                    "Creditor BIC");
        }

        if (!isBlank(request.debtorBic())) {

            validateBic(
                    request.debtorBic(),
                    "Debtor BIC");
        }
    }

    private void validateBic(
            String bic,
            String fieldName) {

        String normalized =
                bic.toUpperCase();

        if (!normalized.matches(
                "^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$")) {

            throw new IllegalArgumentException(
                    fieldName + " has invalid format");
        }
    }

    private void validateSequenceType(
            Pain008Request request) {

        if (!VALID_SEQUENCE_TYPES.contains(
                request.sequenceType().toUpperCase())) {

            throw new IllegalArgumentException(
                    "Invalid SEPA sequence type: "
                            + request.sequenceType());
        }
    }

    private boolean isBlank(String value) {
        return value == null
                || value.isBlank();
    }
}