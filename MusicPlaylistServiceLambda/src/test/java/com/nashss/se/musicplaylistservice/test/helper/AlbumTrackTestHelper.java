package com.nashss.se.musicplaylistservice.test.helper;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.models.PetModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AlbumTrackTestHelper {
    private AlbumTrackTestHelper() {
    }

    public static Pet generateAlbumTrack(int sequenceNumber) {
        Pet pet = new Pet();
        pet.setPetId("asin" + sequenceNumber);
//        pet.setPetName(sequenceNumber);
//        pet.setOwnerId("album" + sequenceNumber);
//        pet.setSongTitle("title" + sequenceNumber);
        return pet;
    }

    public static void assertAlbumTracksEqualSongModels(List<Pet> pets, List<PetModel> petModels) {
        assertEquals(pets.size(),
                     petModels.size(),
                     String.format("Expected album tracks (%s) and song models (%s) to match",
                             pets,
                             petModels));
        for (int i = 0; i < pets.size(); i++) {
            assertAlbumTrackEqualsSongModel(
                pets.get(i),
                petModels.get(i),
                String.format("Expected %dth album track (%s) to match corresponding song model (%s)",
                              i,
                              pets.get(i),
                              petModels.get(i)));
        }
    }

    public static void assertAlbumTrackEqualsSongModel(Pet pet, PetModel petModel) {
        String message = String.format("Expected album track %s to match song model %s", pet, petModel);
        assertAlbumTrackEqualsSongModel(pet, petModel, message);
    }

    public static void assertAlbumTrackEqualsSongModel(Pet pet, PetModel petModel, String message) {
        assertEquals(pet.getPetId(), petModel.getPetId(), message);
//        assertEquals(pet.getPetName(), petModel.getTrackNumber(), message);
//        assertEquals(pet.getOwnerId(), petModel.getOwnerId(), message);
//        assertEquals(pet.getSongTitle(), petModel.getPetName(), message);
    }
}
