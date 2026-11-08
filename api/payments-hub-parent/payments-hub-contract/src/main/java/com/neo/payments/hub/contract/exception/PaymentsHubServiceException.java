package com.neo.payments.hub.contract.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class PaymentsHubServiceException extends  RuntimeException {

    private String code;
    private Map<String, String> errorDescMap;

    public PaymentsHubServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public PaymentsHubServiceException(String code, String message, Exception ex) {
        super(message, ex);
        this.code = code;
    }

    public PaymentsHubServiceException(String message, Map<String, String> errorDescMap) {
        super(message);
        this.errorDescMap = errorDescMap;
    }
}
