package com.neo.api.order.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends  ResponseEntityExceptionHandler {

    private static final String EXCEPTION_STRING = "Exception Occurred {}";

    private static final String COLON = ":";


//    @ExceptionHandler(KafkaPublishException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Object> handleKafkaException(KafkaPublishException ex, WebRequest request) {
//        log.info(EXCEPTION_STRING, ex);
//        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
//                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResult(EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource not found");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        log.info(EXCEPTION_STRING, ex);
        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleAllException(
            Exception ex, WebRequest request) {
        log.info(EXCEPTION_STRING, ex);
        //AlertGenerator.generateErrorAlert((ex.getMessage()));
//        return handleExceptionInternal(ex, new ErrorDetail("an error occurred"),
//                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(
            RuntimeException ex, WebRequest request) {
        log.info(EXCEPTION_STRING, ex);
        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(ApiServiceException.class)
    protected ResponseEntity<Object> handleApiServiceException(
            RuntimeException ex, WebRequest request) {
        log.info(EXCEPTION_STRING, ex);
        return handleExceptionInternal(ex, new ErrorDetail(ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

//    @ResponseBody
//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    String employeeNotFoundHandler(ResourceNotFoundException ex) {
//        return ex.getMessage();
//    }

}
