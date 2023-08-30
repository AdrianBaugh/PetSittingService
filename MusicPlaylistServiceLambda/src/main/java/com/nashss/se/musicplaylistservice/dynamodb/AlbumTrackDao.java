package com.nashss.se.musicplaylistservice.dynamodb;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.exceptions.AlbumTrackNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an album using {@link Pet} to represent the model in DynamoDB.
 */
@Singleton
public class AlbumTrackDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an AlbumTrackDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the album_track table
     */
    @Inject
    public AlbumTrackDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Retrieves an Pet by ASIN and track number.
     *
     * If not found, throws AlbumTrackNotFoundException.
     *
     * @param asin The ASIN to look up
     * @param trackNumber The track number to look up
     * @return The corresponding Pet if found
     */
    public Pet getAlbumTrack(String asin, int trackNumber) {
        Pet pet = dynamoDbMapper.load(Pet.class, asin, trackNumber);
        if (null == pet) {
            throw new AlbumTrackNotFoundException(
                String.format("Could not find Pet with ASIN '%s' and track number %d", asin, trackNumber));
        }

        return pet;
    }
}
