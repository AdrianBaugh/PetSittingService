package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.GetReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.GetReservationResult;
import com.nashss.se.riverpetsittingservice.converters.ModelConverter;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationNotFoundException;

import com.nashss.se.riverpetsittingservice.models.ReservationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
/**
 * Implementation of the GetReservationActivity for the PetService's GetReservation API.
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
    String requestOwnerId = getReservationRequest.getPetOwnerId();
    Reservation reservation = reservationDao.getReservationById(requestOwnerId, requestReservationId);

        if(reservation == null){
            throw new ReservationNotFoundException("Reservation with id "+requestReservationId + " not found");
        }

        ReservationModel reservationModel = new ModelConverter().toReservationModel(reservation);

        return GetReservationResult.builder()
                .withReservation(reservationModel)
                .build();
    }
}