package com.nashss.se.musicplaylistservice.converters;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.models.PlaylistModel;
import com.nashss.se.musicplaylistservice.models.PetModel;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.test.helper.AlbumTrackTestHelper;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.nashss.se.musicplaylistservice.utils.CollectionUtils.copyToSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toPlaylistModel_withTags_convertsPlaylist() {
        Reservation playlist = new Reservation();
        playlist.setReservationId("id");
        playlist.setPetOwnerId("name");
        playlist.setSitterId("customerId");
        playlist.setEndDate(0);
        playlist.setTags(Sets.newHashSet("tag"));

        PlaylistModel playlistModel = modelConverter.toPlaylistModel(playlist);
        assertEquals(playlist.getReservationId(), playlistModel.getId());
        assertEquals(playlist.getPetOwnerId(), playlistModel.getName());
        assertEquals(playlist.getSitterId(), playlistModel.getCustomerId());
        assertEquals(playlist.getEndDate(), playlistModel.getSongCount());
        assertEquals(playlist.getTags(), copyToSet(playlistModel.getTags()));
    }

    @Test
    void toPlaylistModel_nullTags_convertsPlaylist() {
        Reservation playlist = new Reservation();
        playlist.setReservationId("id");
        playlist.setPetOwnerId("name");
        playlist.setSitterId("customerId");
        playlist.setEndDate(0);
        playlist.setTags(null);

        PlaylistModel playlistModel = modelConverter.toPlaylistModel(playlist);
        assertEquals(playlist.getReservationId(), playlistModel.getId());
        assertEquals(playlist.getPetOwnerId(), playlistModel.getName());
        assertEquals(playlist.getSitterId(), playlistModel.getCustomerId());
        assertEquals(playlist.getEndDate(), playlistModel.getSongCount());
        assertNull(playlistModel.getTags());
    }

    @Test
    void toSongModel_withAlbumTrack_convertsToSongModel() {
        // GIVEN
        Pet pet = AlbumTrackTestHelper.generateAlbumTrack(2);

        // WHEN
        PetModel result = modelConverter.toPetModel(pet);

        // THEN
        AlbumTrackTestHelper.assertAlbumTrackEqualsSongModel(
                pet,
            result,
            String.format("Expected album track %s to match song model %s",
                    pet,
                          result)
        );
    }

    @Test
    void toSongModelList_withAlbumTracks_convertsToSongModelList() {
        // GIVEN
        // list of AlbumTracks
        int numTracks = 4;
        List<Pet> pets = new LinkedList<>();
        for (int i = 0; i < numTracks; i++) {
            pets.add(AlbumTrackTestHelper.generateAlbumTrack(i));
        }

        // WHEN
        List<PetModel> result = modelConverter.toSongModelList(pets);

        // THEN
        AlbumTrackTestHelper.assertAlbumTracksEqualSongModels(pets, result);
    }
}
