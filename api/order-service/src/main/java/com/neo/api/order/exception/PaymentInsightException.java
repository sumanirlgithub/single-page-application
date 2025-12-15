package com.neo.api.order.exception;

import lombok.Getter;

@Getter
public class PaymentInsightException extends ApiServiceException {

    private final String errorCode;

    public PaymentInsightException(
            final PaymentInsightExceptionMessage exceptionMessage, final Object... params) {
        super(String.format(exceptionMessage.getErrorMessage(), params));
        this.errorCode = exceptionMessage.getErrorCode();
    }
}
