package com.neo.api.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInsightResponse {

    private String uetrNumber;
    private String statusCode;
    private String reasonCode;
    private String reasonDescription;
    private String reasonType;
    private String status;
    private String reason;
    private String errorMessage;

}
