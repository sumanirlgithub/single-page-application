package com.neo.api.order.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequestPayload {
    @NotBlank
    private String name;
    private String email;
    private LocalDate dob;
}
