package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.exceptions.ReservationNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReservationDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedQueryList<Reservation> paginatedQueryList;
    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<Reservation>> queryCaptor;

    private ReservationDao reservationDao;
    @BeforeEach
    public void setup() {
        initMocks(this);
        this.reservationDao = new ReservationDao(dynamoDBMapper, metricsPublisher);
        // This prevents test from throwing an NPE
        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
    }

    @Test
    public void getReservationById_withCombinationKey_returnsReservation() {
        // GIVEN
        String reservationId = "ReservationId";
        String petOwnerID = "OwnerId";
        when(dynamoDBMapper.load(Reservation.class, petOwnerID, reservationId)).thenReturn(new Reservation());

        // WHEN
        Reservation reservation = reservationDao.getReservationById(petOwnerID, reservationId);

        // THEN
        assertNotNull(reservation);
        verify(dynamoDBMapper).load(Reservation.class, petOwnerID, reservationId);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETRESERVATION_RESERVATIONNOTFOUND_COUNT), anyDouble());
    }

    @Test
    public void getReservationById_ReservationNotFound_throwsException() {
        // GIVEN
        String nonexistentReservationId = "NotReal";
        String nonexistentPetOwnerId = "fake";

        when(dynamoDBMapper.load(Reservation.class, nonexistentPetOwnerId, nonexistentReservationId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ReservationNotFoundException.class, () -> reservationDao.getReservationById(nonexistentPetOwnerId, nonexistentReservationId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETRESERVATION_RESERVATIONNOTFOUND_COUNT), anyDouble());
    }

    @Test
    public void getAllReservationsByOwnerId_withOwnerId_returnsReservationsList() {
        // GIVEN
        String petOwnerID = "OwnerId";

        when(dynamoDBMapper.query(eq(Reservation.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Reservation> results = reservationDao.getAllReservationsByOwnerId(petOwnerID);

        // THEN
        assertNotNull(results);
        assertEquals(paginatedQueryList, results);
        verify(dynamoDBMapper, times(1)).query(eq(Reservation.class), queryCaptor.capture());
    }

    @Test
    public void saveReservation_callsMapperWithReservation() {
        // GIVEN
        Reservation reservation = new Reservation();

        // WHEN
        Reservation result = reservationDao.saveReservation(reservation);

        // THEN
        verify(dynamoDBMapper).save(reservation);
        assertEquals(reservation, result);
    }
}
