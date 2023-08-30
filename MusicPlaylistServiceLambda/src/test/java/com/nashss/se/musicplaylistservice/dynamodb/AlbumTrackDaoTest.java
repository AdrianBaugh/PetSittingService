package com.nashss.se.musicplaylistservice.dynamodb;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.exceptions.AlbumTrackNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlbumTrackDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private AlbumTrackDao albumTrackDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        albumTrackDao = new AlbumTrackDao(dynamoDBMapper);
    }

    @Test
    void getAlbumTrack_withNonexistentAsinAndTrackNumber_loadsAlbumTrackByPartitionAndSortKeys() {
        // GIVEN
        String asin = "asin";
        int trackNumber = 1;
        Pet pet = new Pet();
        pet.setPetId(asin);
        pet.setPetName(trackNumber);
        when(dynamoDBMapper.load(Pet.class, asin, trackNumber)).thenReturn(pet);

        // WHEN
        Pet result = albumTrackDao.getAlbumTrack(asin, trackNumber);

        // THEN
        verify(dynamoDBMapper).load(Pet.class, asin, trackNumber);
        assertEquals(pet, result,
                     String.format("Expected to receive Pet returned by DDB (%s), but received %s",
                             pet,
                                   result)
        );
    }

    @Test
    void getAlbumTrack_withNonexistentAsinOrTrackNumber_throwsException() {
        // GIVEN
        String asin = "NONasin";
        int trackNumber = -1;

        // also part of THEN - expect this mock call
        when(dynamoDBMapper.load(Pet.class, asin, trackNumber)).thenReturn(null);

        // WHEN + THEN
        assertThrows(AlbumTrackNotFoundException.class, () -> albumTrackDao.getAlbumTrack(asin, trackNumber));
    }
}
