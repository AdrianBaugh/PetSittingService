package com.nashss.se.musicplaylistservice.converters;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.models.PlaylistModel;
import com.nashss.se.musicplaylistservice.models.SongModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Reservation} into a {@link PlaylistModel} representation.
     *
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public PlaylistModel toPlaylistModel(Reservation playlist) {
        List<String> tags = null;
        if (playlist.getTags() != null) {
            tags = new ArrayList<>(playlist.getTags());
        }

        return PlaylistModel.builder()
                .withId(playlist.getReservationId())
                .withName(playlist.getPetOwnerId())
                .withCustomerId(playlist.getSitterId())
                .withCustomerName(playlist.getStartDate())
                .withSongCount(playlist.getEndDate())
                .withTags(tags)
                .build();
    }

    /**
     * Converts a provided Pet into a SongModel representation.
     *
     * @param pet the Pet to convert to SongModel
     * @return the converted SongModel with fields mapped from pet
     */
    public SongModel toSongModel(Pet pet) {
        return SongModel.builder()
                .withAsin(pet.getPetId())
                .withTrackNumber(pet.getPetName())
                .withAlbum(pet.getOwnerId())
                .withTitle(pet.getSongTitle())
                .build();
    }

    /**
     * Converts a list of AlbumTracks to a list of SongModels.
     *
     * @param pets The AlbumTracks to convert to SongModels
     * @return The converted list of SongModels
     */
    public List<SongModel> toSongModelList(List<Pet> pets) {
        List<SongModel> songModels = new ArrayList<>();

        for (Pet pet : pets) {
            songModels.add(toSongModel(pet));
        }

        return songModels;
    }

    /**
     * Converts a list of Playlists to a list of PlaylistModels.
     *
     * @param playlists The Playlists to convert to PlaylistModels
     * @return The converted list of PlaylistModels
     */
    public List<PlaylistModel> toPlaylistModelList(List<Reservation> playlists) {
        List<PlaylistModel> playlistModels = new ArrayList<>();

        for (Reservation playlist : playlists) {
            playlistModels.add(toPlaylistModel(playlist));
        }

        return playlistModels;
    }
}
