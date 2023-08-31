package com.nashss.se.musicplaylistservice.converters;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;

import com.nashss.se.musicplaylistservice.models.PetModel;
import com.nashss.se.musicplaylistservice.models.ReservationModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Reservation} into a {@link ReservationModel} representation.
     *
     * @param reservation the reservation to convert
     * @return the converted reservation
     */
    public ReservationModel toReservationModel(Reservation reservation) {

        return ReservationModel.builder()
                .withReservationId(reservation.getReservationId())
                .withPetOwnerId(reservation.getPetOwnerId())
                .withSitterId(reservation.getSitterId())
                .withStartDate(reservation.getStartDate())
                .withEndDate(reservation.getEndDate())
                .withPetList(reservation.getPetList())
                .withStatus(reservation.getStatus())
                .build();
    }

    /**
     * Converts a provided Pet into a PetModel representation.
     *
     * @param pet the Pet to convert to PetModel
     * @return the converted PetModel with fields mapped from pet
     */
    public PetModel toPetModel(Pet pet) {
        return PetModel.builder()
                .withPetId(pet.getPetId())
                .withPetName(pet.getPetName())
                .withOwnerId(pet.getOwnerId())
                .build();
    }

    /**
     * Converts a list of pets to a list of PetModels.
     *
     * @param pets The pets to convert to PetModels
     * @return The converted list of PetModels
     */
    public List<PetModel> toPetModelList(List<Pet> pets) {
        List<PetModel> petModels = new ArrayList<>();

        for (Pet pet : pets) {
            petModels.add(toPetModel(pet));
        }

        return petModels;
    }

    /**
     * Converts a list of Reservations to a list of Reservation Models.
     *
     * @param reservations The Reservations to convert to ReservationsModels
     * @return The converted list of ReservationModels
     */
    public List<ReservationModel> toReservationModelList(List<Reservation> reservations) {
        List<ReservationModel> reservationModels = new ArrayList<>();

        for (Reservation reservation : reservations) {
            reservationModels.add(toReservationModel(reservation));
        }

        return reservationModels;
    }
}
