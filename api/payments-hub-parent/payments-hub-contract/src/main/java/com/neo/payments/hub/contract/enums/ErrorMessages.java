package com.neo.payments.hub.contract.enums;

import lombok.Getter;

/**
 * This class has all the error codes and description
 */
@Getter
public enum ErrorMessages {
    UNKNOWN_ERROR("unknown.error", "Unknown error while processing the request. Please contact support team");

    private final String errorCode;
    private final String errorDesc;

    ErrorMessages(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
