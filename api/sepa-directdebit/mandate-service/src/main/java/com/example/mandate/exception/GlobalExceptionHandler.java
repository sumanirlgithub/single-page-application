package com.example.mandate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdempotencyKeyConflictException.class)
    public ResponseEntity<Map<String, String>> handleIdempotencyConflict(
            IdempotencyKeyConflictException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "status", "409",
                        "message", ex.getMessage()
                ));
    }
}