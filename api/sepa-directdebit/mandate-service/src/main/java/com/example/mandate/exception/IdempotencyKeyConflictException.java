package com.example.mandate.exception;

public class IdempotencyKeyConflictException
        extends RuntimeException {

    public IdempotencyKeyConflictException(String message) {
        super(message);
    }
}