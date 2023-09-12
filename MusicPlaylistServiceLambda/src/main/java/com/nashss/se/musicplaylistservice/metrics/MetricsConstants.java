package com.nashss.se.musicplaylistservice.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETRESERVATION_RESERVATIONNOTFOUND_COUNT =
            "GetReservation.ReservationNotFoundException.Count";
    public static final String UPDATERESERVATION_INVALIDATTRIBUTEVALUE_COUNT =
        "UpdateReservation.InvalidAttributeValueException.Count";
    public static final String UPDATERESERVATION_INVALIDATTRIBUTECHANGE_COUNT =
        "UpdateReservation.InvalidAttributeChangeException.Count";
    public static final String UPDATERESERVATION_UNAUTHORIZED_COUNT =
            "UpdateReservation.UnauthorizedOwnerException.Count";
    public static final String CANCELRESERVATION_RESERVATIONNOTFOUND_COUNT =
            "CancelReservation.ReservationNotFoundException.Count";
    public static final String GETPET_PETNOTFOUND_COUNT =
            "GetPet.PetNotFoundException.Count";
    public static final String UPDATEPET_INVALIDATTRIBUTEVALUE_COUNT =
            "UpdatePet.InvalidAttributeValueException.Count";
    public static final String UPDATEPET_INVALIDATTRIBUTECHANGE_COUNT =
            "UpdatePet.InvalidAttributeChangeException.Count";
    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "RiverPetSittingService";
    public static final String NAMESPACE_NAME = "U5-Midstone-Chocolate/RiverPetSittingService";
}
