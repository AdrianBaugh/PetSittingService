package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.CancelReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CancelReservationResult;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
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
        log.info("received cancelReservationRequest {}", cancelReservationRequest);
        String ownerId = cancelReservationRequest.getPetOwnerId();
        String reservationId = cancelReservationRequest.getReservationId();

        String result = reservationDao.deleteReservation(ownerId, reservationId);

        return new CancelReservationResult.Builder()
                .withDeleteResult(result)
                .build();
    }
}
