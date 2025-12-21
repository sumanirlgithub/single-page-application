package com.neo.payment.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusRes {

    private String uetrNumber;
    private String statusCode;
    private String reasonCode;
    private String reasonDescription;
    private String reasonType;
    private String status;
    private String reason;
    private String errorMessage;
}
