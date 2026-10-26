package com.example.mandate.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreditorRequest(

        @NotBlank
        String name,

        @NotBlank
        String creditorId,

        @NotBlank
        String iban,

        String bic
) { }

