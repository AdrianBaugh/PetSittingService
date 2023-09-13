package com.nashss.se.musicplaylistservice.dependency;

import com.nashss.se.musicplaylistservice.activity.*;

import com.nashss.se.musicplaylistservice.activity.requests.CancelReservationRequest;
import dagger.Component;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Reservation Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /*

     */
    CreatePetActivity provideCreatePetActivity();
    /*

     */
    GetPetActivity provideGetPetActivity();
    /*

     */
    GetReservationActivity provideGetReservationActivity();
    /*

     */
    CreateReservationActivity provideCreateReservationActivity();
    /*

     */
    GetAllPetsActivity provideGetAllPetsActivity();
    /*

     */
    GetAllReservationsActivity provideGetAllReservationsActivity();
    /*

     */
    UpdateReservationActivity provideUpdateReservationActivity();
    /*

     */
    CancelReservationActivity provideCancelReservationActivity();

}
