package com.nashss.se.riverpetsittingservice.exceptions;

/**
 * Exception to throw when a given Reservation is not found in the database.
 */
public class ReservationNotFoundException extends ReservationException {

    private static final long serialVersionUID = -912326717789387971L;

    /**
     * Exception with no message or cause.
     */
    public ReservationNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ReservationNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ReservationNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with a message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
