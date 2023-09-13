package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.UpdateReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateReservationResult;
import com.nashss.se.musicplaylistservice.converters.LocalDateConverter;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateReservationActivity {

    private final Logger log = LogManager.getLogger();
    private final ReservationDao reservationDao;
    private final MetricsPublisher metricsPublisher;
    @Inject
    public UpdateReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }


    public UpdateReservationResult handleRequest(final UpdateReservationRequest updateReservationRequest) {
        log.info("Received the Update reservation request {}", updateReservationRequest);

        LocalDateConverter converter = new LocalDateConverter();

        Reservation reservation = reservationDao.getReservationById(updateReservationRequest.getPetOwnerId(), updateReservationRequest.getReservationId());

        reservation.setStartDate(converter.unconvert(updateReservationRequest.getStartDate()));
        reservation.setEndDate(converter.unconvert(updateReservationRequest.getEndDate()));
        reservation = reservationDao.saveReservation(reservation);

        return UpdateReservationResult.builder()
                .withReservation(new ModelConverter().toReservationModel(reservation))
                .build();

        }
}
