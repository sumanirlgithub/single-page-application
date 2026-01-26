package com.neo.api.order.exception;

/**
 * Base Runtime Exception for API Services for indicating that an error has been occurred
 * and request could not be completed due to a conflict with the current state of the resource.
 */
public class OrderServiceException extends RuntimeException {

    /**
     * Initialize exception without Message.
     */
    public OrderServiceException() { super();}

    /**
     * Initialize the exception using {@code message}.
     *
     * @param message The message describing the exception
     */
    public OrderServiceException(String message) {super(message); }

    /**
     * Initialize the exception using {@code message} and {@code cause}.
     *
     * @param message The message describing the exception
     * @param cause The underlying cause of the exception
     */
    public OrderServiceException(String message, Throwable cause) {super(message, cause);}


}
