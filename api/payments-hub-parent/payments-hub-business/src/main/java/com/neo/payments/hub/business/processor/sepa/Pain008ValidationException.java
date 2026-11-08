package com.neo.payments.hub.business.processor.sepa;


public class Pain008ValidationException extends RuntimeException {

    public Pain008ValidationException(String message) {
        super(message);
    }

    public Pain008ValidationException(
            String message,
            Throwable cause) {
        super(message, cause);
    }
}
