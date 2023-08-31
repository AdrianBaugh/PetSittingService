package com.nashss.se.musicplaylistservice.exceptions;
/**
 * Exception to throw when a UnauthorizedOwner or user is not found in the database.
 */
public class UnauthorizedOwnerException extends RuntimeException{
    private static final long serialVersionUID = -912326717789387971L;

    /**
     * Exception with no message or cause.
     */
    public UnauthorizedOwnerException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public UnauthorizedOwnerException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public UnauthorizedOwnerException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public UnauthorizedOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
