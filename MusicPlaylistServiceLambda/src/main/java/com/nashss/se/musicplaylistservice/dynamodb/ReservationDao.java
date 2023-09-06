package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ReservationDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a PlaylistDao object.
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
     * Retrieves a reservation by its ID.
     *
     * @param reservationId The ID of the reservation to retrieve.
     * @return The reservation object if found, or null if not found.
     */
    public Reservation getReservationById(String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        DynamoDBQueryExpression<Reservation> queryExpression = new DynamoDBQueryExpression<Reservation>()
                .withHashKeyValues(reservation);

        List<Reservation> reservations = dynamoDbMapper.query(Reservation.class, queryExpression);

        if (reservations != null && !reservations.isEmpty()) {
            return reservations.get(0); // Assuming reservation IDs are unique
        }

        return null; // Reservation not found
    }
}
