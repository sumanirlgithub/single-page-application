package com.neo.payment.exception;

/**
 * Base Runtime Exception for API Services for indicating that an error has been occurred
 * and request could not be completed due to a conflict with the current state of the resource.
 */
public class PaymentServiceException extends RuntimeException {

    /**
     * Initialize exception without Message.
     */
    public PaymentServiceException() { super();}

    /**
     * Initialize the exception using {@code message}.
     *
     * @param message The message describing the exception
     */
    public PaymentServiceException(String message) {super(message); }

    /**
     * Initialize the exception using {@code message} and {@code cause}.
     *
     * @param message The message describing the exception
     * @param cause The underlying cause of the exception
     */
    public PaymentServiceException(String message, Throwable cause) {super(message, cause);}


}
