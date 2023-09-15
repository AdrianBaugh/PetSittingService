package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.CreateReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CreateReservationResult;

import com.nashss.se.riverpetsittingservice.converters.LocalDateConverter;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;

import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;

import com.nashss.se.riverpetsittingservice.exceptions.ReservationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateReservationActivityTest {
    @Mock
    private ReservationDao reservationDao;

    private CreateReservationActivity createReservationActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createReservationActivity = new CreateReservationActivity(reservationDao);

    }

    @Test
    public void handleRequest_withValidFields_createsAndSavesReservation() {
        LocalDateConverter converter = new LocalDateConverter();

       LocalDate expectedStartDate = LocalDate.now();

       LocalDate expectedEndDate = LocalDate.now().plusDays(4);

       List<String> petList = new ArrayList<>();
       petList.add("Fluffers");


       String petOwnerId = "test@gmail.com";

        CreateReservationRequest request = CreateReservationRequest.builder()
                .withStartDate(converter.convert(expectedStartDate))
                .withEndDate(converter.convert(expectedEndDate))
                .withPetList(petList)
                .withPetOwnerId(petOwnerId)
                .build();

        CreateReservationResult result = createReservationActivity.handleRequest(request);

        verify(reservationDao).saveReservation(any(Reservation.class));

        assertNotNull(result.getReservation().getReservationId());
        assertNotNull(result.getReservation().getStatus());

        assertEquals(expectedEndDate, result.getReservation().getEndDate());
    }
}

