package com.neo.payment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInsightRequestOrg {

    @Size(min=1, max = 5, message = "{error.UETR_NUMBER_REQUIRED}")
    private List<String> uetrNumbers;

}
