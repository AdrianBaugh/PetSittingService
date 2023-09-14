package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.CreateReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CreateReservationResult;
import com.nashss.se.riverpetsittingservice.converters.LocalDateConverter;
import com.nashss.se.riverpetsittingservice.converters.ModelConverter;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;

import com.nashss.se.riverpetsittingservice.exceptions.ReservationException;
import com.nashss.se.riverpetsittingservice.models.ReservationModel;
import com.nashss.se.riverpetsittingservice.utils.IdUtils;
import com.nashss.se.riverpetsittingservice.utils.SitterEnum;
import com.nashss.se.riverpetsittingservice.utils.StatusEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;


public class CreateReservationActivity {

    private final Logger log = LogManager.getLogger();
    private final ReservationDao reservationDao;
    /**
     * Instantiates a new CreateReservationActivity object.
     *
     * @param reservationDao ReservationDao to access the reservations table.
     */
    @Inject
    public CreateReservationActivity(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public CreateReservationResult handleRequest(final CreateReservationRequest createReservationRequest) throws ReservationException {
        log.info("received CreateReservationRequest {}", createReservationRequest);

        LocalDateConverter converter = new LocalDateConverter();
        LocalDate startDate = converter.unconvert(createReservationRequest.getStartDate());
        if (!(startDate.compareTo(LocalDate.now()) >= 0)) {
            throw new ReservationException("Start date cannot be scheduled in the past...");
        }
        LocalDate endDate = converter.unconvert(createReservationRequest.getEndDate());
        if (endDate.isBefore(startDate)) {
            throw new ReservationException("End date cannot be before Start date...");
        }

        Reservation newReservation = new Reservation();
        newReservation.setReservationId(IdUtils.generateReservationId());
        newReservation.setStartDate(startDate);
        newReservation.setEndDate(endDate);
        newReservation.setStatus(String.valueOf(StatusEnum.UPCOMING));
        newReservation.setPetList(createReservationRequest.getPetList());
        newReservation.setSitterId(String.valueOf(SitterEnum.SITTER_1));
        newReservation.setPetOwnerId(createReservationRequest.getPetOwnerId());

        reservationDao.saveReservation(newReservation);

        ReservationModel reservationModel = new ModelConverter().toReservationModel(newReservation);
        return CreateReservationResult.builder()
                .withReservation(reservationModel)
                .build();
    }


}
