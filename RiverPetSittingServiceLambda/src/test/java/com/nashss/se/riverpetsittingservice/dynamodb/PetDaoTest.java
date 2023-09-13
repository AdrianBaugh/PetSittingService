package com.nashss.se.riverpetsittingservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import com.nashss.se.riverpetsittingservice.dynamodb.models.Pet;
import com.nashss.se.riverpetsittingservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PetDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedQueryList<Pet> paginatedQueryList;

    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<Pet>> queryCaptor;

    PetDao petDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        petDao = new PetDao(mapper, metricsPublisher);
        when(mapper.query(eq(Pet.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // This prevents test from throwing an NPE
        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
    }

    @Test
    void getPetById_withValidIt_returnsPet() {
    }

    @Test
    void savePet_withProperFields_savesPet() {
    }

    @Test
    void getAllPets_withValidOwnerId_returnsCorrectListOfPetsFromGSI() {
        //Given
        String ownerId = "Justin1";
        Pet pet1 = new Pet();
        pet1.setPetId("1234");
        pet1.setPetName("Fido");
        pet1.setPetId("ABcd");
        pet1.setOwnerName("Justin");
        pet1.setOwnerId(ownerId);

        Pet pet2 = new Pet();
        pet2.setPetId("4567");
        pet2.setPetName("Zobo");
        pet2.setPetId("Bdo3");
        pet2.setOwnerName("Justin");
        pet2.setOwnerId(ownerId);

        List<Pet> petList = new ArrayList<>();
        petList.add(pet1);
        petList.add(pet2);

        //When
        List<Pet> results = petDao.getAllPets(ownerId);

        // Then
        assertEquals(paginatedQueryList, results);

        verify(mapper, times(1)).query(eq(Pet.class), queryCaptor.capture());
    }
}