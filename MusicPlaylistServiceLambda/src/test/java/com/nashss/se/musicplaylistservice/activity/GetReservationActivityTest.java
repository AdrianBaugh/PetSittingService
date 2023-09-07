package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetReservationResponse;
import com.nashss.se.musicplaylistservice.dynamodb.ReservationDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetReservationActivityTest {
    @Mock
    private ReservationDao reservationDao;
    private GetReservationActivity getReservationActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getReservationActivity = new GetReservationActivity(reservationDao);
    }

    @Test
    public void handleRequest_withValidFields_getReservationSuccess() {
        // GIVEN
        String reservationId = "666666";
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        //WHEN
        when(reservationDao.getReservationById(reservationId)).thenReturn(reservation);

        GetReservationRequest reservationRequest = GetReservationRequest.builder()
                .withReservationId(reservationId)
                .build();
        GetReservationResponse reservationResponse = getReservationActivity.handleRequest(reservationRequest);

        //THEN
        assertEquals(reservationId,reservationResponse.getReservation().getReservationId());
    }
}
