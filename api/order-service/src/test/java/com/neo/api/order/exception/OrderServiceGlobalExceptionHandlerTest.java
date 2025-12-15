package com.neo.api.order.exception;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceGlobalExceptionHandlerTest {

    private OrderServiceGlobalExceptionHandler orderServiceExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    void setup() {
        orderServiceExceptionHandler = new OrderServiceGlobalExceptionHandler();
    }

    @Test
    void GivenOrderServiceException_ThenHttpResponseStatusIs500AndWithExceptionMessageAsError() {
        final ResponseEntity<Object> responseEntity = orderServiceExceptionHandler.handleApiServiceException(
                new OrderServiceException("Some API Service Exception"), webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertErrorBody(responseEntity.getBody(), "Some API Service Exception");
        verifyNoInteractions(webRequest);
    }

    @Test
    void handleConflict() {
        final ResponseEntity<Object> responseEntity = orderServiceExceptionHandler.handleConflict(
                new IllegalStateException("Some Error"), webRequest);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertErrorBody(responseEntity.getBody(), "Some Error");
        verifyNoInteractions(webRequest);
    }

    @Test
    void GivenMethodArgumentNotValidException_thenHttpResponseStatusCodeIs400() {
        HttpHeaders headers = new HttpHeaders();
        FieldError fieldError = new FieldError("", "", "Error in length");
        BindingResult bindingResult = new DirectFieldBindingResult(fieldError, "fieldError");
        bindingResult.addError(fieldError);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        final ResponseEntity<Object> responseEntity = orderServiceExceptionHandler.handleMethodArgumentNotValid(
                methodArgumentNotValidException, headers, HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verifyNoInteractions(webRequest);
    }

    private void assertErrorBody(Object responseBody, String expectedError) {
        assertInstanceOf(ErrorDetail.class, responseBody);
        ErrorDetail errorDetail = (ErrorDetail) responseBody;
        assertEquals(expectedError, errorDetail.getError());
    }
}
