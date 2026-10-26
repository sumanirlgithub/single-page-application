package com.example.mandate.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateMandateRequest(

        @NotNull
        @Valid
        DebtorRequest debtor,

        @NotNull
        @Valid
        CreditorRequest creditor,

        @NotBlank
        String mandateType,

        @NotNull
        LocalDate signatureDate
) { }

