package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.dynamodb.PetDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CreatePetActivityTest {
    @Mock
    private PetDao petDao;

    private CreatePetActivity createPetActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createPetActivity = new CreatePetActivity(petDao);
    }

    @Test
    public void handleRequest_withValidName_() {
    }
}