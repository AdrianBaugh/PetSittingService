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
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public ReservationModel toPlaylistModel(Reservation playlist) {
        List<String> tags = null;
        if (playlist.getTags() != null) {
            tags = new ArrayList<>(playlist.getTags());
        }

        return ReservationModel.builder()
                .withId(playlist.getReservationId())
                .withName(playlist.getPetOwnerId())
                .withCustomerId(playlist.getSitterId())
                .withCustomerName(playlist.getStartDate())
                .withSongCount(playlist.getEndDate())
                .withTags(tags)
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
     * Converts a list of AlbumTracks to a list of SongModels.
     *
     * @param pets The AlbumTracks to convert to SongModels
     * @return The converted list of SongModels
     */
    public List<PetModel> toSongModelList(List<Pet> pets) {
        List<PetModel> petModels = new ArrayList<>();

        for (Pet pet : pets) {
            petModels.add(toPetModel(pet));
        }

        return petModels;
    }

    /**
     * Converts a list of Playlists to a list of PlaylistModels.
     *
     * @param playlists The Playlists to convert to PlaylistModels
     * @return The converted list of PlaylistModels
     */
    public List<ReservationModel> toPlaylistModelList(List<Reservation> playlists) {
        List<ReservationModel> playlistModels = new ArrayList<>();

        for (Reservation playlist : playlists) {
            playlistModels.add(toPlaylistModel(playlist));
        }

        return playlistModels;
    }
}
