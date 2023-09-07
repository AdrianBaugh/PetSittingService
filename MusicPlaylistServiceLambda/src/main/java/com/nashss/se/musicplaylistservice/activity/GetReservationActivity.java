package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetReservationResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.exceptions.ReservationNotFoundException;

import com.nashss.se.musicplaylistservice.models.ReservationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
/**
 * Implementation of the GetReservationActivity for the PetService's GetReservation API.
 *
 * This API allows the customer to get one of their saved reservations.
 */
public class GetReservationActivity{
    private final Logger log = LogManager.getLogger();
    private final ReservationDao reservationDao;
    /**
     * Instantiates a new GetReservationActivity object.
     *
     * @param reservationDao ReservationDao to access the reservation table.
     */
    @Inject
    public GetReservationActivity(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }
    /**
     * This method handles the incoming request by retrieving the reservation from the database.
     * <p>
     * It then returns the reservation.
     * <p>
     * If the reservation does not exist, this should throw a ReservationNotFoundException.
     *
     * @param getReservationRequest request object containing the reservation ID
     * @return getReservationResponse result object containing the API defined {@link ReservationModel}
     */
    public GetReservationResult handleRequest(final GetReservationRequest getReservationRequest){
    log.info("Received GetReservationRequest {}", getReservationRequest.getReservationId());
    String requestReservationId  = getReservationRequest.getReservationId();
    Reservation reservation = reservationDao.getReservationById(requestReservationId);

        if(reservation == null){
            throw new ReservationNotFoundException("Reservation with id "+requestReservationId + " not found");
        }

        ReservationModel reservationModel = new ModelConverter().toReservationModel(reservation);

        return GetReservationResult.builder()
                .withReservation(reservationModel)
                .build();
    }
}