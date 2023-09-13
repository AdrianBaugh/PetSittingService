package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.UpdateReservationRequest;

import com.nashss.se.musicplaylistservice.activity.results.UpdateReservationResult;

import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;

import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateReservationActivityTest {
    @Mock
    private ReservationDao reservationDao;
    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateReservationActivity updateReservationActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateReservationActivity = new UpdateReservationActivity(reservationDao, metricsPublisher);
    }

    @Test
    public void handleRequest_goodRequest_updatesReservationDates() {

        // GIVEN
        String petOwnerId = "Bob";
        String reservationId = "expectedReservationId";
        LocalDate expectedStartDate = LocalDate.parse("2023-09-12");
        LocalDate expectedEndDate = LocalDate.parse("2023-09-15");

        UpdateReservationRequest request = UpdateReservationRequest.builder()
                                            .withReservationId(reservationId)
                                            .withPetOwnerId(petOwnerId)
                                            .withEndDate(String.valueOf(expectedEndDate))
                                            .withStartDate(String.valueOf(expectedStartDate))
                                            .build();

        Reservation startingReservation = new Reservation();
        startingReservation.setReservationId(reservationId);
        startingReservation.setPetOwnerId(petOwnerId);
        startingReservation.setEndDate(LocalDate.parse("2023-09-20"));
        startingReservation.setStartDate(LocalDate.parse("2023-09-10"));

        when(reservationDao.getReservationById(petOwnerId, reservationId)).thenReturn(startingReservation);
        when(reservationDao.saveReservation(startingReservation)).thenReturn(startingReservation);

        // WHEN
        UpdateReservationResult result = updateReservationActivity.handleRequest(request);

        // THEN
        assertEquals(expectedStartDate, result.getReservation().getStartDate());
        assertEquals(expectedEndDate, result.getReservation().getEndDate());
    }

}
