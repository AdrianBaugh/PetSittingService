package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.GetReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.GetReservationResult;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Reservation;

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
        String ownerId = "ownerID";
        String reservationId = "666666";
        Reservation reservation = new Reservation();
        reservation.setPetOwnerId(ownerId);
        reservation.setReservationId(reservationId);

        //WHEN
        when(reservationDao.getReservationById(ownerId, reservationId)).thenReturn(reservation);

        GetReservationRequest reservationRequest = GetReservationRequest.builder()
                .withReservationId(reservationId)
                .withOwnerId(ownerId)
                .build();
        GetReservationResult reservationResponse = getReservationActivity.handleRequest(reservationRequest);

        //THEN
        assertEquals(reservationId,reservationResponse.getReservation().getReservationId());
    }
}
