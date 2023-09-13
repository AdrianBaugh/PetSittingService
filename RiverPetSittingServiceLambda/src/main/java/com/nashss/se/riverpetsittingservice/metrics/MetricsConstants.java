package com.nashss.se.riverpetsittingservice.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETRESERVATION_RESERVATIONNOTFOUND_COUNT =
            "GetReservation.ReservationNotFoundException.Count";

    public static final String CANCELRESERVATION_RESERVATIONNOTFOUND_COUNT =
            "CancelReservation.ReservationNotFoundException.Count";
    public static final String GETPET_PETNOTFOUND_COUNT =
            "GetPet.PetNotFoundException.Count";

    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "RiverPetSittingService";
    public static final String NAMESPACE_NAME = "U5-Midstone-Chocolate/RiverPetSittingService";
}
