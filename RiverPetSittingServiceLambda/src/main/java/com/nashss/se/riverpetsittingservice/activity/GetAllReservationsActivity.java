package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.GetAllReservationsRequest;
import com.nashss.se.riverpetsittingservice.activity.results.GetAllReservationsResult;
import com.nashss.se.riverpetsittingservice.converters.ModelConverter;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationNotFoundException;
import com.nashss.se.riverpetsittingservice.models.ReservationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

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

    /**
     * This method handles the incoming request by retrieving all the reservations for a user from the database.
     * <p>
     * It then returns the reservation list.
     * <p>
     * If no reservation does not exist, this should throw a ReservationNotFoundException.
     *
     * @param getAllReservationsRequest request object containing the petOwner ID to look up reservations for
     * @return GetAllReservationsResult result object containing the reservation's list of API-defined ReservationModel
     */
    public GetAllReservationsResult handleRequest(final GetAllReservationsRequest getAllReservationsRequest) throws ReservationNotFoundException {
        log.info("Received GetAllReservationsRequest {}", getAllReservationsRequest);

        List<Reservation> reservations = reservationDao.getAllReservationsByOwnerId(getAllReservationsRequest.getPetOwnerId());
        List<ReservationModel> reservationModels = new ModelConverter().toReservationModelList(reservations);

        return GetAllReservationsResult.builder()
                .withReservationList(reservationModels)
                .build();
    }
}
