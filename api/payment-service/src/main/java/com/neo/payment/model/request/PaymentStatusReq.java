package com.neo.payment.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusReq {

    @Size(min=1, max = 5, message = "{error.UETR_NUMBER_REQUIRED}")
    private List<String> uetrNumbers;
}
