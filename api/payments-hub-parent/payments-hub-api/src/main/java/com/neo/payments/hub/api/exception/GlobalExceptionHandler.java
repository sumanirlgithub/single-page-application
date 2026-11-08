package com.neo.payments.hub.api.exception;

import com.neo.payments.hub.api.util.ResponseUtils;
import com.neo.payments.hub.contract.RestAPIResponse;
import com.neo.payments.hub.contract.enums.ErrorMessages;
import com.neo.payments.hub.contract.exception.PaymentsHubServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestAPIResponse<Object> returnInternalServerErrorMessage(HttpServletRequest request, Exception ex) {
        log.error("Unhandled exception", ex);
        log.error("Unhandled exception: {} method={} URI={}", ex, request.getMethod(), request.getRequestURI());
        return ResponseUtils.error(ErrorMessages.UNKNOWN_ERROR.name(), ErrorMessages.UNKNOWN_ERROR.getErrorDesc());
    }

    @ExceptionHandler(PaymentsHubServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestAPIResponse<Object> handlePaymentsHubServiceError(HttpServletRequest request, PaymentsHubServiceException ex) {
        log.error("PaymentsHubServiceException error: {} method={} URI={}", ex, request.getMethod(), request.getRequestURI());
        return ex.getErrorDescMap() != null ? ResponseUtils.errorWithDesc(ex.getMessage(), ex.getErrorDescMap())
                : ResponseUtils.error(ex.getCode(), ex.getMessage());
    }
}
