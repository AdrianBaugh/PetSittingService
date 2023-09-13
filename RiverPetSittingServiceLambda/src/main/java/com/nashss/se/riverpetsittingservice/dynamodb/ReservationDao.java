package com.nashss.se.riverpetsittingservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationNotFoundException;
import com.nashss.se.riverpetsittingservice.metrics.MetricsConstants;
import com.nashss.se.riverpetsittingservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ReservationDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a ReservationDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public ReservationDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Retrieves a reservation by its IDs.
     *
     * @param reservationId The ID of the reservation to retrieve.
     * @return The reservation object if found, or null if not found.
     */
    public Reservation getReservationById(String ownerId, String reservationId) {
        Reservation reservation = dynamoDbMapper.load(Reservation.class, ownerId, reservationId);
        if (reservation == null) {
            metricsPublisher.addCount(MetricsConstants.GETRESERVATION_RESERVATIONNOTFOUND_COUNT, 1);
            throw new ReservationNotFoundException("Reservation not found");
        }
        metricsPublisher.addCount(MetricsConstants.GETRESERVATION_RESERVATIONNOTFOUND_COUNT, 0);
        return reservation;
    }

    public List<Reservation> getAllReservationsByOwnerId(String ownerId) {

        Reservation reservation = new Reservation();
        reservation.setPetOwnerId(ownerId);

        DynamoDBQueryExpression<Reservation> queryExpression = new DynamoDBQueryExpression<Reservation>()
                .withHashKeyValues(reservation);

        List<Reservation> reservationList = dynamoDbMapper.query(Reservation.class, queryExpression);

        if (reservationList == null || reservationList.isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.GETRESERVATION_RESERVATIONNOTFOUND_COUNT, 1);
            throw new ReservationNotFoundException("Nothing inside of Reservation list");
        }

        metricsPublisher.addCount(MetricsConstants.GETRESERVATION_RESERVATIONNOTFOUND_COUNT, 0);
        return reservationList;
    }

    public Reservation saveReservation(Reservation newReservation) {
        this.dynamoDbMapper.save(newReservation);
        return newReservation;

    }

    public String deleteReservation(String ownerId, String reservationId) {

        Reservation reservationToDelete = new Reservation();

        reservationToDelete.setPetOwnerId(ownerId);
        reservationToDelete.setReservationId(reservationId);

        dynamoDbMapper.delete(reservationToDelete);

        metricsPublisher.addCount(MetricsConstants.CANCELRESERVATION_RESERVATIONNOTFOUND_COUNT, 0);
        return String.format("Reservation, %s, successfully canceled.", reservationId);
    }
}
