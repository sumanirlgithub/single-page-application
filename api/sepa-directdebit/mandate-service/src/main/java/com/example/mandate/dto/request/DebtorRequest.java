package com.example.mandate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DebtorRequest(

        @NotBlank(message = "name is required")
        String name,

        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9]{15,34}$")
        String iban,

        String bic
) { }
