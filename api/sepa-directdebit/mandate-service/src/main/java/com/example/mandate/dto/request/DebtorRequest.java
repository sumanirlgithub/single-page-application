package com.example.mandate.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DebtorRequest(

        @NotBlank(message = "name is required")
        String name,

        @NotBlank
        String iban,

        String bic
) { }
