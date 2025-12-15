package com.neo.api.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentInsightExceptionMessage {

    API_CALL_EXCEPTION("PI10001", "WebClient call to payment insight api failed." + " error : %s"),
    PAYMENT_NUMBER_NOT_FOUND("PI10002", "Payment number not found for the UETR "
            + "number received." + "UETR number : %s"),
    INVALID_PAYMENT_STATUS("PI10003", "Payment status %s received is not valid."),
    DB_SAVE_FAILED("PI10004", "Exception occured while saving status in DB %s"),
    INVALID_PAYMENT_NUMBER("PI10005", "Invalid payment number."),
    INVALID_PAYMENT_INSIGHT_STATUS("PI10006", "Invalid Payment Insight Status for Manual refresh"),
    UETR_NO_BLANK("PI10007",  "UETR number not found for the payment number %s");

    private String errorCode;
    private String errorMessage;

}
