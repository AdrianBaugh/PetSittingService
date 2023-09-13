package com.nashss.se.riverpetsittingservice.dependency;

import com.nashss.se.riverpetsittingservice.activity.*;

import dagger.Component;

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
