package com.neo.payments.hub.contract;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RestAPIResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorDesc;
    private String errorCode;
    private Map<String, String> errorDescMap;
    private LocalDateTime dateTime;
    private String redirectUrl;
}
