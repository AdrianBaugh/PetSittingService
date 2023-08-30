package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetPlaylistSongsRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetPlaylistSongsResult;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.models.SongOrder;
import com.nashss.se.musicplaylistservice.models.SongModel;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.musicplaylistservice.exceptions.PlaylistNotFoundException;
import com.nashss.se.musicplaylistservice.test.helper.AlbumTrackTestHelper;
import com.nashss.se.musicplaylistservice.test.helper.PlaylistTestHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetReservationSongsActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    private GetPlaylistSongsActivity getPlaylistSongsActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        getPlaylistSongsActivity = new GetPlaylistSongsActivity(playlistDao);
    }

    @Test
    void handleRequest_playlistExistsWithSongs_returnsSongsInPlaylist() {
        // GIVEN
        Reservation playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(3);
        String playlistId = playlist.getReservationId();
        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
                                              .withId(playlistId)
                                              .build();
        when(playlistDao.getPlaylist(playlistId)).thenReturn(playlist);

        // WHEN
        GetPlaylistSongsResult result = getPlaylistSongsActivity.handleRequest(request);

        // THEN
        AlbumTrackTestHelper.assertAlbumTracksEqualSongModels(playlist.getPetList(), result.getSongList());
    }

    @Test
    void handleRequest_playlistExistsWithoutSongs_returnsEmptyList() {
        // GIVEN
        Reservation emptyPlaylist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(0);
        String playlistId = emptyPlaylist.getReservationId();
        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
                                              .withId(playlistId)
                                              .build();
        when(playlistDao.getPlaylist(playlistId)).thenReturn(emptyPlaylist);

        // WHEN
        GetPlaylistSongsResult result = getPlaylistSongsActivity.handleRequest(request);

        // THEN
        assertTrue(result.getSongList().isEmpty(),
                   "Expected song list to be empty but was " + result.getSongList());
    }

    @Test
    void handleRequest_withDefaultSongOrder_returnsDefaultOrderedPlaylistSongs() {
        // GIVEN
        Reservation playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(10);
        String playlistId = playlist.getReservationId();

        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
                                              .withId(playlistId)
                                              .withOrder(SongOrder.DEFAULT)
                                              .build();
        when(playlistDao.getPlaylist(playlistId)).thenReturn(playlist);

        // WHEN
        GetPlaylistSongsResult result = getPlaylistSongsActivity.handleRequest(request);

        // THEN
        AlbumTrackTestHelper.assertAlbumTracksEqualSongModels(playlist.getPetList(), result.getSongList());
    }

    @Test
    void handleRequest_withReversedSongOrder_returnsReversedPlaylistSongs() {
        // GIVEN
        Reservation playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(9);
        String playlistId = playlist.getReservationId();
        List<Pet> reversedPets = new LinkedList<>(playlist.getPetList());
        Collections.reverse(reversedPets);

        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
                                              .withId(playlistId)
                                              .withOrder(SongOrder.REVERSED)
                                              .build();
        when(playlistDao.getPlaylist(playlistId)).thenReturn(playlist);

        // WHEN
        GetPlaylistSongsResult result = getPlaylistSongsActivity.handleRequest(request);

        // THEN
        AlbumTrackTestHelper.assertAlbumTracksEqualSongModels(reversedPets, result.getSongList());
    }

    @Test
    void handleRequest_withShuffledSongOrder_returnsSongsInAnyOrder() {
        Reservation playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(8);
        String playlistId = playlist.getReservationId();

        List<SongModel> songModels = new ModelConverter().toSongModelList(playlist.getPetList());

        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
                                              .withId(playlistId)
                                              .withOrder(SongOrder.REVERSED)
                                              .build();
        when(playlistDao.getPlaylist(playlistId)).thenReturn(playlist);

        // WHEN
        GetPlaylistSongsResult result = getPlaylistSongsActivity.handleRequest(request);

        // THEN
        assertEquals(playlist.getPetList().size(),
                     result.getSongList().size(),
                     String.format("Expected album tracks (%s) and song models (%s) to be the same length",
                                   playlist.getPetList(),
                                   result.getSongList()));
        assertTrue(
            songModels.containsAll(result.getSongList()),
            String.format("album list (%s) and song models (%s) are the same length, but don't contain the same " +
                          "entries. Expected song models: %s. Returned song models: %s",
                          playlist.getPetList(),
                          result.getSongList(),
                          songModels,
                          result.getSongList()));
    }

    @Test
    public void handleRequest_noMatchingPlaylistId_throwsPlaylistNotFoundException() {
        // GIVEN
        String id = "missingID";
        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
                                              .withId(id)
                                              .build();

        // WHEN
        when(playlistDao.getPlaylist(id)).thenThrow(new PlaylistNotFoundException());

        // WHEN + THEN
        assertThrows(PlaylistNotFoundException.class, () -> getPlaylistSongsActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_withInvalidSongOrder_throwsException() {
        // GIVEN
        Reservation playlist = PlaylistTestHelper.generatePlaylist();
        String id = playlist.getReservationId();
        GetPlaylistSongsRequest request = GetPlaylistSongsRequest.builder()
            .withId(id)
            .withOrder("NOT A VALID ORDER")
            .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> getPlaylistSongsActivity.handleRequest(request));
    }
}
