package com.nashss.se.riverpetsittingservice.activity;
import com.nashss.se.riverpetsittingservice.activity.requests.GetPetRequest;
import com.nashss.se.riverpetsittingservice.activity.results.GetPetResult;
import com.nashss.se.riverpetsittingservice.dynamodb.PetDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Pet;
import com.nashss.se.riverpetsittingservice.models.PetModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetPetActivityTest {

    @Mock
    private PetDao petDao;
    private GetPetActivity getPetActivity;

    @BeforeEach
    void setUp(){
        openMocks(this);
        getPetActivity = new GetPetActivity(petDao);
    }

    @Test
    public void handleRequest_withValidPetID_getPet(){
        // GIVEN
        String expectedPetId = "validPetID";
        Pet expectedPet = new Pet();
        expectedPet.setPetId(expectedPetId);
        expectedPet.setPetName("Coco");

        when(petDao.getPetById(expectedPetId)).thenReturn(expectedPet);

        GetPetRequest request = new GetPetRequest(expectedPetId);

        //WHEN
        GetPetResult result= getPetActivity.handleRequest(request);

        //THEN
        assertNotNull(result);
        PetModel petModel = result.getPet();
        assertNotNull(petModel);
        assertEquals(expectedPetId, petModel.getPetId());
        assertEquals("Coco", petModel.getPetName());
    }
}