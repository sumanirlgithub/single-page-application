 package com.example.mandate.service;

 import com.example.mandate.domain.SepaMandate;
 import com.example.mandate.dto.request.CreateMandateRequest;
 import com.example.mandate.dto.response.MandateResponse;
 import com.example.mandate.exception.DuplicateMandateException;
 import com.example.mandate.exception.InvalidMandateException;
 import com.example.mandate.exception.MandateNotFoundException;
 import com.example.mandate.mapper.MandateMapper;
 import com.example.mandate.repository.MandateRepository;

 import java.time.Instant;
 import java.time.LocalDate;
 import java.util.Locale;
 import java.util.UUID;

 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;

 @Service
 public class MandateService {

     private final MandateRepository mandateRepository;
     private final MandateMapper mandateMapper;

     public MandateService(
             MandateRepository mandateRepository,
             MandateMapper mandateMapper) {

         this.mandateRepository = mandateRepository;
         this.mandateMapper = mandateMapper;
     }

     @Transactional
     public MandateResponse createMandate(
             CreateMandateRequest request) {

         validateMandateRequest(request);

         String debtorIban = normalise(request.debtor().iban());
         String creditorIban = normalise(request.creditor().iban());
         String mandateType = normalise(request.mandateType());

         if (mandateRepository
                 .existsActiveMandate(
                         debtorIban,
                         request.creditor().creditorId().trim())) {

             throw new DuplicateMandateException(
                     "An active mandate already exists for this debtor "
                             + "and creditor."
             );
         }

         // Generate the mandate ID inside Mandate Service.
         String mandateId = generateMandateId();

         // Illustrative initial status.
         String status = "ACTIVE";

         Instant now = Instant.now();

         // Create the internal domain object.
         SepaMandate mandate = mandateMapper.toDomain(
                 new CreateMandateRequest(
                         new com.example.mandate.dto.request.DebtorRequest(
                                 request.debtor().name().trim(),
                                 debtorIban,
                                 normaliseOptional(request.debtor().bic())
                         ),
                         new com.example.mandate.dto.request.CreditorRequest(
                                 request.creditor().name().trim(),
                                 request.creditor().creditorId().trim(),
                                 creditorIban,
                                 normaliseOptional(request.creditor().bic())
                         ),
                         mandateType,
                         request.signatureDate()
                 ),
                 mandateId,
                 status,
                 now
         );

         // Persist before returning a successful response.
         mandateRepository.save(mandate);

         return mandateMapper.toResponse(mandate);
     }

     @Transactional(readOnly = true)
     public MandateResponse getMandate(String mandateId) {

         SepaMandate mandate = mandateRepository.findById(mandateId)
                 .orElseThrow(() -> new MandateNotFoundException(
                         "Mandate not found: " + mandateId
                 ));

         return mandateMapper.toResponse(mandate);
     }

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

     private void validateIban(String iban, String party) {

         // Basic format check only; this does not validate the IBAN checksum.
         String normalisedIban = normalise(iban);

         if (!normalisedIban.matches("[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}")) {
             throw new InvalidMandateException(
                     party + " IBAN has an invalid format."
             );
         }
     }

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

     private String generateMandateId() {
         return "MAND-" + UUID.randomUUID()
                 .toString()
                 .replace("-", "")
                 .toUpperCase(Locale.ROOT);
     }

     private String normalise(String value) {
         return value == null
                 ? ""
                 : value.trim().toUpperCase(Locale.ROOT);
     }

     private String normaliseOptional(String value) {
         return value == null || value.isBlank()
                 ? null
                 : value.trim().toUpperCase(Locale.ROOT);
     }

     private boolean isBlank(String value) {
         return value == null || value.isBlank();
     }
 }