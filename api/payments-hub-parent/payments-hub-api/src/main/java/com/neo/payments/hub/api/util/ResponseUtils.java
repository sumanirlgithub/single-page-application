package com.neo.payments.hub.api.util;

import com.neo.payments.hub.contract.RestAPIResponse;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseUtils {

    public static <T>RestAPIResponse<T> success(T data, String message) {
        RestAPIResponse<T> response = new RestAPIResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setDateTime(LocalDateTime.now());
        return response;
    }

    public static <T>RestAPIResponse<T> error(String errorCode, String errorDesc) {
        RestAPIResponse<T> response = new RestAPIResponse<>();
        response.setSuccess(false);
        response.setMessage(null);
        response.setData(null);
        response.setErrorCode(errorCode);
        response.setErrorDesc(errorDesc);
        response.setDateTime(LocalDateTime.now());
        return response;
    }

    public static <T>RestAPIResponse<T> errorWithDesc(String errorCode, Map<String, String> errorDescMap) {
        RestAPIResponse<T> response = new RestAPIResponse<>();
        response.setSuccess(false);
        response.setMessage(null);
        response.setData(null);
        response.setErrorDesc(errorCode);
        response.setErrorDescMap(errorDescMap);
        response.setDateTime(LocalDateTime.now());
        return response;
    }

}
