package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetAllReservationsActivity for the PetSittingService's GetAllReservations API.
 * <p>
 * This API allows the customer to get the list of reservations they currently have.
 */
public class GetAllReservationsActivity {

    private final Logger log = LogManager.getLogger();
    private final ReservationDao reservationDao;

    /**
     * Instantiates a new GetAllReservationsActivity object.
     *
     * @param reservationDao ReservationDao to access the reservation table.
     */
    @Inject
    public GetAllReservationsActivity(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }
}
