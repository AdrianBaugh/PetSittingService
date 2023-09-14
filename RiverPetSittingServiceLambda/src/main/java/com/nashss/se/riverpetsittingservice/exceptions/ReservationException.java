package com.nashss.se.riverpetsittingservice.exceptions;

public class ReservationException extends Exception {

    /**
     * Exception with no message or cause.
     */
    public ReservationException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ReservationException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ReservationException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with a message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}

