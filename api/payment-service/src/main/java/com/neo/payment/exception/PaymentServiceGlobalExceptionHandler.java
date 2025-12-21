package com.neo.payment.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.neo.payment.exception.ExceptionMessageConstants.EXCEPTION_STRING;

@RestControllerAdvice
@Slf4j
public class PaymentServiceGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${request.max.uetrs:5}")
    private String maxUetrs;

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest webRequest) {
        log.error(EXCEPTION_STRING, ex);
        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    /**
     * Provides handling for Api Service Exceptions
     *
     * @param ex the target instance of Api Service Exception
     * @param request the current request
     * @return a {@code ResponseEntity} instance with Http Internal Server Error Status
     */
    @ExceptionHandler(value = {PaymentServiceException.class})
    protected ResponseEntity<Object> handleApiServiceException(RuntimeException ex,
                                                               WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Provides handling for Method Argument Not Valid Exceptions.
     *
     * @param ex            the target instance of MethodArgumentNotValid Exception
     * @param headers       the http headers
     * @param statusCode    the http status code
     * @param request       the current request
     * @return a {@code ResponseEntity} instance with Http Bad request Status
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        log.error(EXCEPTION_STRING, ex);
        StringBuilder builder = new StringBuilder();
        if (ex.getBindingResult().hasFieldErrors()) {
            List<String> errorList = ex.getBindingResult().getFieldErrors()
                    .stream().map(this::getCustomMessage)
                    .toList();
            errorList.forEach(builder::append);
        }
        return this.handleExceptionInternal(ex, new ErrorDetail(builder.toString()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private String getCustomMessage(FieldError error) {
        return error.getField()
                .concat(StringUtils.SPACE)
                .concat(":")
                .concat(StringUtils.SPACE)
                .concat(error.getDefaultMessage())
                .concat(".")
                .concat(StringUtils.SPACE)
                .concat(String.valueOf(maxUetrs));
    }

}
