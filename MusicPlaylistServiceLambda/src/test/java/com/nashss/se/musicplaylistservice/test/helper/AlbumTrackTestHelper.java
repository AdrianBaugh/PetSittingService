package com.nashss.se.musicplaylistservice.test.helper;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.models.SongModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AlbumTrackTestHelper {
    private AlbumTrackTestHelper() {
    }

    public static Pet generateAlbumTrack(int sequenceNumber) {
        Pet pet = new Pet();
        pet.setPetId("asin" + sequenceNumber);
        pet.setPetName(sequenceNumber);
        pet.setOwnerId("album" + sequenceNumber);
        pet.setSongTitle("title" + sequenceNumber);
        return pet;
    }

    public static void assertAlbumTracksEqualSongModels(List<Pet> pets, List<SongModel> songModels) {
        assertEquals(pets.size(),
                     songModels.size(),
                     String.format("Expected album tracks (%s) and song models (%s) to match",
                             pets,
                                   songModels));
        for (int i = 0; i < pets.size(); i++) {
            assertAlbumTrackEqualsSongModel(
                pets.get(i),
                songModels.get(i),
                String.format("Expected %dth album track (%s) to match corresponding song model (%s)",
                              i,
                              pets.get(i),
                              songModels.get(i)));
        }
    }

    public static void assertAlbumTrackEqualsSongModel(Pet pet, SongModel songModel) {
        String message = String.format("Expected album track %s to match song model %s", pet, songModel);
        assertAlbumTrackEqualsSongModel(pet, songModel, message);
    }

    public static void assertAlbumTrackEqualsSongModel(Pet pet, SongModel songModel, String message) {
        assertEquals(pet.getPetId(), songModel.getAsin(), message);
        assertEquals(pet.getPetName(), songModel.getTrackNumber(), message);
        assertEquals(pet.getOwnerId(), songModel.getAlbum(), message);
        assertEquals(pet.getSongTitle(), songModel.getTitle(), message);
    }
}
