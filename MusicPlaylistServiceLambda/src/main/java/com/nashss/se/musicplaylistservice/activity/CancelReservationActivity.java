package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.CancelReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.CancelReservationResult;
import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class CancelReservationActivity {
    private final Logger log = LogManager.getLogger();
    private final ReservationDao reservationDao;

    @Inject
    public CancelReservationActivity(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public CancelReservationResult handleRequest(final CancelReservationRequest cancelReservationRequest) {
            String ownerId = cancelReservationRequest.getPetOwnerId();
            String reservationId = cancelReservationRequest.getReservationId();

            Boolean result = reservationDao.deleteReservation(ownerId, reservationId);

        return new CancelReservationResult.Builder()
                .withDeleteResult(result)
                .build();
    }
}
