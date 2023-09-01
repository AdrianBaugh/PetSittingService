package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.results.CreatePetResult;
import com.nashss.se.musicplaylistservice.models.PetModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CreatePetResultTest {
    @Test
    public void testCreatePetResultBuilder() {
        PetModel mockPetModel = mock(PetModel.class);

        CreatePetResult result = CreatePetResult.builder()
                .withPet(mockPetModel)
                .build();

        assertEquals(mockPetModel, result.getPet());
    }
}
