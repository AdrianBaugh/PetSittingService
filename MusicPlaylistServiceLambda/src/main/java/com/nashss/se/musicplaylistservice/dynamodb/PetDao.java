package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.exceptions.PetIdNotFoundException;
import com.nashss.se.musicplaylistservice.exceptions.ReservationNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import javax.inject.Inject;

public class PetDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a PetDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the pets table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public PetDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }
    /**
     * Returns the {@link Pet} corresponding to the specified id.
     *
     * @param id the Pet ID
     * @return the stored Pet, or null if none was found.
     */
    public Pet getPetById(String id) {
        Pet pet = this.dynamoDbMapper.load(Pet.class, id);

        if (pet == null) {
            metricsPublisher.addCount(MetricsConstants.GETPET_PETNOTFOUND_COUNT, 1);
            throw new PetIdNotFoundException("Could not find pet with id " + id);
        }
        metricsPublisher.addCount(MetricsConstants.GETPET_PETNOTFOUND_COUNT, 0);
        return pet;
    }
    public Pet savePet(Pet newPet) {
        this.dynamoDbMapper.save(newPet);
        return newPet;
    }
}
