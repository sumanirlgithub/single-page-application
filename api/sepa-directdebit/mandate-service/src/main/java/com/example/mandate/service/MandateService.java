
package com.example.mandate.service;

import com.example.mandate.domain.SepaMandate;
import com.example.mandate.dto.request.CreateMandateRequest;
import com.example.mandate.dto.request.DebtorRequest;
import com.example.mandate.dto.request.CreditorRequest;
import com.example.mandate.dto.response.MandateResponse;
import com.example.mandate.exception.IdempotencyKeyConflictException;
import com.example.mandate.exception.InvalidMandateException;
import com.example.mandate.exception.MandateNotFoundException;
import com.example.mandate.mapper.MandateMapper;
import com.example.mandate.repository.IdempotencyRepository;
import com.example.mandate.repository.MandateRepository;
import com.example.mandate.util.MandateReferenceGenerator;
import com.example.mandate.util.RequestHashGenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MandateService {

    private static final String OPERATION_NAME = "CREATE_MANDATE";
    private static final int SUCCESS_HTTP_STATUS = 201;

    private final MandateRepository mandateRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final MandateMapper mandateMapper;
    private final MandateReferenceGenerator mandateReferenceGenerator;
    private final RequestHashGenerator requestHashGenerator;
    private final ObjectMapper objectMapper;

    /**
     * Creates a SEPA mandate.
     *
     * The mandate insert and idempotency completion update
     * are committed together in one database transaction.
     */
    @Transactional
    public MandateResponse createMandate(
            CreateMandateRequest request,
            String idempotencyKey) {

        // 1. Validate request.
        validateMandateRequest(request);
        validateIdempotencyKey(idempotencyKey);

        // 2. Normalise request data.
        CreateMandateRequest normalisedRequest =
                normaliseRequest(request);

        // 3. Generate request hash.
        String requestHash =
                requestHashGenerator.generate(normalisedRequest);

        // 4. Claim idempotency key.
        boolean newRequest = idempotencyRepository.insertIfAbsent(
                idempotencyKey,
                OPERATION_NAME,
                requestHash
        );

        // 5. Handle an existing idempotency key.
        if (!newRequest) {

            IdempotencyRepository.IdempotencyRecord existing =
                    idempotencyRepository.findByKey(
                            idempotencyKey,
                            OPERATION_NAME
                    ).orElseThrow(() ->
                            new IllegalStateException(
                                    "Idempotency record not found"
                            )
                    );

            // Reject reuse of the same key with a different request.
            if (!existing.requestHash().equals(requestHash)) {

                throw new IdempotencyKeyConflictException(
                        "Idempotency key has already been used " +
                                "with a different request"
                );
            }

            // Return the original response for a completed request.
            if ("COMPLETED".equals(existing.status())) {

                return deserializeResponse(
                        existing.responseBody()
                );
            }

            // Defensive handling for an incomplete record.
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Request is currently being processed"
            );
        }

        // 6. Generate mandate identifiers.
        String mandateId = generateMandateId();

        String idUmr = mandateReferenceGenerator.generate();


        // 7. Determine initial mandate status.
        // Change this according to your mandate activation policy.
        String status = "ACTIVE";

        Instant now = Instant.now();

        // 8. Convert request DTO into domain object.
        SepaMandate mandate = mandateMapper.toDomain(
                normalisedRequest,
                mandateId,
                idUmr,
                status,
                now
        );

        // 9. Insert mandate into SEPA_MANDATE table.
        mandateRepository.save(mandate);

        // 10. Convert domain object into API response.
        MandateResponse response =
                mandateMapper.toResponse(mandate);

        // 11. Serialize response for idempotency storage.
        String responseBody = serializeResponse(response);

        // 12. Mark idempotency record as completed.
        idempotencyRepository.markCompleted(
                idempotencyKey,
                OPERATION_NAME,
                mandateId,
                SUCCESS_HTTP_STATUS,
                responseBody
        );

        // 13. Return response.
        // Spring commits both database operations after this method succeeds.
        return response;
    }

    /**
     * Retrieves an existing mandate using its internal mandate ID.
     */
    @Transactional(readOnly = true)
    public MandateResponse getMandate(String idUmr) {

        SepaMandate mandate = mandateRepository.findByIdUmr(idUmr)
                .orElseThrow(() ->
                        new MandateNotFoundException(
                                "Mandate not found: " + idUmr
                        )
                );

        return mandateMapper.toResponse(mandate);
    }

    /**
     * Normalises request fields before hashing and persistence.
     */
    private CreateMandateRequest normaliseRequest(
            CreateMandateRequest request) {

        DebtorRequest debtor = new DebtorRequest(
                request.debtor().name().trim(),
                normalise(request.debtor().iban()),
                normaliseOptional(request.debtor().bic())
        );

        CreditorRequest creditor = new CreditorRequest(
                request.creditor().name().trim(),
                request.creditor().creditorId().trim(),
                normalise(request.creditor().iban()),
                normaliseOptional(request.creditor().bic())
        );

        return new CreateMandateRequest(
                debtor,
                creditor,
                normalise(request.mandateType()),
                request.signatureDate()
        );
    }

    /**
     * Validates mandatory request fields and business rules.
     */
    private void validateMandateRequest(
            CreateMandateRequest request) {

        if (request == null
                || request.debtor() == null
                || request.creditor() == null) {

            throw new InvalidMandateException(
                    "Debtor and creditor details are required."
            );
        }

        if (isBlank(request.debtor().name())
                || isBlank(request.creditor().name())
                || isBlank(request.creditor().creditorId())) {

            throw new InvalidMandateException(
                    "Debtor name, creditor name and creditor ID are required."
            );
        }

        if (isBlank(request.debtor().iban())
                || isBlank(request.creditor().iban())) {

            throw new InvalidMandateException(
                    "Debtor IBAN and creditor IBAN are required."
            );
        }

        if (request.signatureDate() == null) {

            throw new InvalidMandateException(
                    "Signature date is required."
            );
        }

        if (request.signatureDate().isAfter(LocalDate.now())) {

            throw new InvalidMandateException(
                    "Signature date cannot be in the future."
            );
        }

        String mandateType = normalise(request.mandateType());

        if (!"CORE".equals(mandateType)
                && !"B2B".equals(mandateType)) {

            throw new InvalidMandateException(
                    "Mandate type must be CORE or B2B."
            );
        }

        validateIban(request.debtor().iban(), "Debtor");
        validateIban(request.creditor().iban(), "Creditor");

        validateOptionalBic(request.debtor().bic(), "Debtor");
        validateOptionalBic(request.creditor().bic(), "Creditor");
    }

    /**
     * Validates Idempotency-Key header.
     */
    private void validateIdempotencyKey(String idempotencyKey) {

        if (idempotencyKey == null
                || idempotencyKey.isBlank()
                || idempotencyKey.length() > 100) {

            throw new InvalidMandateException(
                    "A valid Idempotency-Key header is required. " +
                            "Maximum length is 100 characters."
            );
        }
    }

    /**
     * Performs basic IBAN format validation.
     * This does not validate the IBAN checksum.
     */
    private void validateIban(String iban, String party) {

        String normalisedIban = normalise(iban);

        if (!normalisedIban.matches(
                "[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}")) {

            throw new InvalidMandateException(
                    party + " IBAN has an invalid format."
            );
        }
    }

    /**
     * Validates optional BIC.
     */
    private void validateOptionalBic(String bic, String party) {

        if (bic == null || bic.isBlank()) {
            return;
        }

        String normalisedBic = normalise(bic);

        if (!normalisedBic.matches(
                "[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?")) {

            throw new InvalidMandateException(
                    party + " BIC has an invalid format."
            );
        }
    }

    /**
     * Generates internal mandate ID.
     */
    private String generateMandateId() {

        return "MAND-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .toUpperCase(Locale.ROOT);
    }

    /**
     * Normalises mandatory string values.
     */
    private String normalise(String value) {

        return value == null
                ? ""
                : value.trim().toUpperCase(Locale.ROOT);
    }

    /**
     * Normalises optional string values.
     */
    private String normaliseOptional(String value) {

        return value == null || value.isBlank()
                ? null
                : value.trim().toUpperCase(Locale.ROOT);
    }

    private boolean isBlank(String value) {

        return value == null || value.isBlank();
    }

    /**
     * Serializes API response before storing it in API_IDEMPOTENCY.
     */
    private String serializeResponse(MandateResponse response) {

        try {

            return objectMapper.writeValueAsString(response);

        } catch (JsonProcessingException ex) {

            throw new IllegalStateException(
                    "Unable to serialize mandate response",
                    ex
            );
        }
    }

    /**
     * Deserializes the original API response during an idempotent retry.
     */
    private MandateResponse deserializeResponse(String responseBody) {

        if (responseBody == null || responseBody.isBlank()) {

            throw new IllegalStateException(
                    "Stored idempotency response is empty"
            );
        }

        try {

            return objectMapper.readValue(
                    responseBody,
                    MandateResponse.class
            );

        } catch (JsonProcessingException ex) {

            throw new IllegalStateException(
                    "Unable to deserialize stored mandate response",
                    ex
            );
        }
    }
}
