package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.CancelReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CancelReservationResult;
import com.nashss.se.riverpetsittingservice.dynamodb.ReservationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CancelReservationActivityTest {

    @InjectMocks
    private CancelReservationActivity activity;
    @Mock
    private ReservationDao reservationDao;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void handleRequest_AttemptsToDeleteReservation() {
        // GIVEN
        String reservationId = "ReservationId";
        String petOwnerID = "OwnerId";
        String successString = String.format("Reservation, %s, successfully canceled.", reservationId);
        CancelReservationRequest request = new CancelReservationRequest(reservationId, petOwnerID);
        when(reservationDao.deleteReservation(petOwnerID, reservationId)).thenReturn(successString);

        // WHEN
        CancelReservationResult result = activity.handleRequest(request);

        // THEN
        verify(reservationDao).deleteReservation(petOwnerID, reservationId);
        assertEquals(successString, result.getDeleteResult());
    }
}
