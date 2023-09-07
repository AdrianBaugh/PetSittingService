package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.CreateReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreateReservationResult;
import com.nashss.se.musicplaylistservice.converters.LocalDateConverter;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;

import com.nashss.se.musicplaylistservice.models.ReservationModel;
import com.nashss.se.musicplaylistservice.utils.IdUtils;
import com.nashss.se.musicplaylistservice.utils.SitterEnum;
import com.nashss.se.musicplaylistservice.utils.StatusEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


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


    public CreateReservationResult handleRequest(final CreateReservationRequest createReservationRequest) {
        log.info("received CreateReservationRequest {}", createReservationRequest);

        LocalDateConverter converter = new LocalDateConverter();

        Reservation newReservation = new Reservation();
        newReservation.setReservationId(IdUtils.generateReservationId());
        newReservation.setStartDate(converter.unconvert(createReservationRequest.getStartDate()));
        newReservation.setEndDate(converter.unconvert(createReservationRequest.getEndDate()));
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
