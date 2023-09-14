package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.UpdateReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.UpdateReservationResult;
import com.nashss.se.riverpetsittingservice.converters.LocalDateConverter;
import com.nashss.se.riverpetsittingservice.converters.ModelConverter;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationException;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationNotFoundException;
import com.nashss.se.riverpetsittingservice.metrics.MetricsPublisher;

import com.nashss.se.riverpetsittingservice.utils.StatusEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UpdateReservationActivity {

    private final Logger log = LogManager.getLogger();
    private final ReservationDao reservationDao;
    private final MetricsPublisher metricsPublisher;
    @Inject
    public UpdateReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }


    public UpdateReservationResult handleRequest(final UpdateReservationRequest updateReservationRequest) throws ReservationException {
        log.info("Received the Update reservation request {}", updateReservationRequest);

        LocalDateConverter converter = new LocalDateConverter();

        Reservation reservation = reservationDao.getReservationById(updateReservationRequest.getPetOwnerId(), updateReservationRequest.getReservationId());
        LocalDate updatedStartDate = converter.unconvert(updateReservationRequest.getStartDate());
        LocalDate updatedEndDate = converter.unconvert(updateReservationRequest.getEndDate());
        String reservationStatus = reservation.getStatus();

        if (Objects.equals(reservationStatus, String.valueOf(StatusEnum.COMPLETE))) {
            throw new ReservationException("Cannot update a Completed reservation...");
        }

        if (!(updatedStartDate.compareTo(LocalDate.now()) > 0) && updatedStartDate.compareTo(reservation.getStartDate()) != 0) {
            throw new ReservationException("Cannot update reservation to Start before today...");
        }
        if (Objects.equals(reservationStatus, String.valueOf(StatusEnum.IN_PROGRESS)) && !updatedStartDate.isEqual(reservation.getStartDate())) {
            throw new ReservationException("Cannot update the Start date of a reservation in progress. Start date: " + reservation.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));
        }

        if (updatedEndDate.isBefore(updatedStartDate)) {
            throw new ReservationException("Cannot make End date before Start date...");
        }

        reservation.setStartDate(updatedStartDate);
        reservation.setEndDate(updatedEndDate);
        reservation = reservationDao.saveReservation(reservation);

        return UpdateReservationResult.builder()
                .withReservation(new ModelConverter().toReservationModel(reservation))
                .build();

        }
}
