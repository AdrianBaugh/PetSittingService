package com.nashss.se.riverpetsittingservice.exceptions;

/**
 * Exception to throw when a given Pet ID is not found
 * MusicPlaylistServiceLambda/src/main/java/com/nashss/se/riverpetsittingservice/exceptions/PetIdNotFoundException.java
 * in the database.
 */
public class PetIdNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1230785223023147290L;

    /**
     * Exception with no message or cause.
     */
    public PetIdNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public PetIdNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public PetIdNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public PetIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
