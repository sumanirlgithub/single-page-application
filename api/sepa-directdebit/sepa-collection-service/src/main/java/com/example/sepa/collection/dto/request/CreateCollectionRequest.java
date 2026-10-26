package com.example.sepa.collection.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCollectionRequest(

        @NotBlank
        String mandateId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotBlank
        @Pattern(regexp = "[A-Za-z]{3}")
        String currency,

        @NotNull
        LocalDate requestedCollectionDate,

        @NotBlank
        @Pattern(regexp = "FRST|RCUR|OOFF|FNAL")
        String sequenceType

) {}