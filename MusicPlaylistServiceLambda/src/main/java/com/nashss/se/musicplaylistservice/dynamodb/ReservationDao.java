package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public Reservation saveReservation(Reservation newReservation) {
        this.dynamoDbMapper.save(newReservation);
        return newReservation;
    }
}
