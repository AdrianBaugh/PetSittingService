package com.nashss.se.riverpetsittingservice.exceptions;
/**
 * Exception to throw when a given sitter ID is not found in the database.
 */
public class SitterNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 5594991634843817514L;

    /**
     * Exception with no message or cause.
     */
    public SitterNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public SitterNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public SitterNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with a message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public SitterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

