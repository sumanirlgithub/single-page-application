package com.example.mandate.mapper;

import com.example.mandate.domain.SepaMandate;
import com.example.mandate.dto.request.CreateMandateRequest;
import com.example.mandate.dto.response.CreditorResponse;
import com.example.mandate.dto.response.DebtorResponse;
import com.example.mandate.dto.response.MandateResponse;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class MandateMapper {

    public SepaMandate toDomain(
            CreateMandateRequest request,
            String mandateId,
            String status,
            Instant now) {

        return new SepaMandate(
                mandateId,
                request.debtor().name(),
                request.debtor().iban(),
                request.debtor().bic(),
                request.creditor().name(),
                request.creditor().creditorId(),
                request.creditor().iban(),
                request.creditor().bic(),
                request.mandateType(),
                request.signatureDate(),
                status,
                now,
                now
        );
    }

    public MandateResponse toResponse(SepaMandate mandate) {

        DebtorResponse debtor = new DebtorResponse(
                mandate.debtorName(),
                mandate.debtorIban(),
                mandate.debtorBic()
        );

        CreditorResponse creditor = new CreditorResponse(
                mandate.creditorName(),
                mandate.creditorId(),
                mandate.creditorIban(),
                mandate.creditorBic()
        );

        return new MandateResponse(
                mandate.mandateId(),
                mandate.status(),
                debtor,
                creditor,
                mandate.mandateType(),
                mandate.signatureDate(),
                mandate.createdAt(),
                mandate.updatedAt()
        );
    }
}