package com.citi.issuer.sharedservices.payment.common.errors;

public class ApiServiceException extends RuntimeException {

    /**
     * Initialize exception without Message.
     */
    public ApiServiceException() { super();}

    /**
     * Initialize the exception using {@code message}.
     *
     * @param message The message describing the exception
     */
    public ApiServiceException(String message) {super(message); }

    /**
     * Initialize the exception using {@code message} and {@code cause}.
     *
     * @param message The message describing the exception
     * @param cause The underlying cause of the exception
     */
    public ApiServiceException(String message, Throwable cause) {super(message, cause);}
}
