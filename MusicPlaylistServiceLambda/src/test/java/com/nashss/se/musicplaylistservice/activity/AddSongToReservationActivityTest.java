package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.AddSongToPlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddSongToPlaylistResult;
import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.models.SongModel;
import com.nashss.se.musicplaylistservice.dynamodb.AlbumTrackDao;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.exceptions.AlbumTrackNotFoundException;
import com.nashss.se.musicplaylistservice.exceptions.PlaylistNotFoundException;
import com.nashss.se.musicplaylistservice.test.helper.AlbumTrackTestHelper;
import com.nashss.se.musicplaylistservice.test.helper.PlaylistTestHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddSongToReservationActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    @Mock
    private AlbumTrackDao albumTrackDao;

    private AddSongToPlaylistActivity addSongToPlaylistActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        addSongToPlaylistActivity = new AddSongToPlaylistActivity(playlistDao, albumTrackDao);
    }

    @Test
    void handleRequest_validRequest_addsSongToEndOfPlaylist() {
        // GIVEN
        // a non-empty playlist
        Reservation originalPlaylist = PlaylistTestHelper.generatePlaylist();
        String playlistId = originalPlaylist.getReservationId();
        String customerId = originalPlaylist.getSitterId();

        // the new song to add to the playlist
        Pet petToAdd = AlbumTrackTestHelper.generateAlbumTrack(2);
        String addedAsin = petToAdd.getPetId();
        int addedTracknumber = petToAdd.getPetName();

        when(playlistDao.getPlaylist(playlistId)).thenReturn(originalPlaylist);
        when(playlistDao.savePlaylist(originalPlaylist)).thenReturn(originalPlaylist);
        when(albumTrackDao.getAlbumTrack(addedAsin, addedTracknumber)).thenReturn(petToAdd);

        AddSongToPlaylistRequest request = AddSongToPlaylistRequest.builder()
            .withId(playlistId)
            .withAsin(addedAsin)
            .withTrackNumber(addedTracknumber)
            .withCustomerId(customerId)
            .build();

        // WHEN
        AddSongToPlaylistResult result = addSongToPlaylistActivity.handleRequest(request);

        // THEN
        verify(playlistDao).savePlaylist(originalPlaylist);

        assertEquals(2, result.getSongList().size());
        SongModel secondSong = result.getSongList().get(1);
        AlbumTrackTestHelper.assertAlbumTrackEqualsSongModel(petToAdd, secondSong);
    }

    @Test
    public void handleRequest_noMatchingPlaylistId_throwsPlaylistNotFoundException() {
        // GIVEN
        String playlistId = "missing id";
        AddSongToPlaylistRequest request = AddSongToPlaylistRequest.builder()
                                               .withId(playlistId)
                                               .withAsin("asin")
                                               .withTrackNumber(1)
                                               .withCustomerId("doesn't matter")
                                               .build();
        when(playlistDao.getPlaylist(playlistId)).thenThrow(new PlaylistNotFoundException());

        // WHEN + THEN
        assertThrows(PlaylistNotFoundException.class, () -> addSongToPlaylistActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_noMatchingAlbumTrack_throwsAlbumTrackNotFoundException() {
        // GIVEN
        Reservation playlist = PlaylistTestHelper.generatePlaylist();

        String playlistId = playlist.getReservationId();
        String cusomerId = playlist.getSitterId();
        String asin = "nonexistent asin";
        int trackNumber = -1;
        AddSongToPlaylistRequest request = AddSongToPlaylistRequest.builder()
                                               .withId(playlistId)
                                               .withAsin(asin)
                                               .withTrackNumber(trackNumber)
                                               .withCustomerId(cusomerId)
                                               .build();

        // WHEN
        when(playlistDao.getPlaylist(playlistId)).thenReturn(playlist);
        when(albumTrackDao.getAlbumTrack(asin, trackNumber)).thenThrow(new AlbumTrackNotFoundException());

        // THEN
        assertThrows(AlbumTrackNotFoundException.class, () -> addSongToPlaylistActivity.handleRequest(request));
    }

    @Test
    void handleRequest_validRequestWithQueueNextFalse_addsSongToEndOfPlaylist() {
        // GIVEN
        int startingTrackCount = 3;
        Reservation originalPlaylist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(startingTrackCount);
        String playlistId = originalPlaylist.getReservationId();
        String customerId = originalPlaylist.getSitterId();

        // the new song to add to the playlist
        Pet petToAdd = AlbumTrackTestHelper.generateAlbumTrack(8);
        String addedAsin = petToAdd.getPetId();
        int addedTracknumber = petToAdd.getPetName();

        when(playlistDao.getPlaylist(playlistId)).thenReturn(originalPlaylist);
        when(playlistDao.savePlaylist(originalPlaylist)).thenReturn(originalPlaylist);
        when(albumTrackDao.getAlbumTrack(addedAsin, addedTracknumber)).thenReturn(petToAdd);

        AddSongToPlaylistRequest request = AddSongToPlaylistRequest.builder()
                                               .withId(playlistId)
                                               .withAsin(addedAsin)
                                               .withTrackNumber(addedTracknumber)
                                               .withQueueNext(false)
                                               .withCustomerId(customerId)
                                               .build();

        // WHEN
        AddSongToPlaylistResult result = addSongToPlaylistActivity.handleRequest(request);

        // THEN
        verify(playlistDao).savePlaylist(originalPlaylist);

        assertEquals(startingTrackCount + 1, result.getSongList().size());
        SongModel lastSong = result.getSongList().get(result.getSongList().size() - 1);
        AlbumTrackTestHelper.assertAlbumTrackEqualsSongModel(petToAdd, lastSong);
    }

    @Test
    void handleRequest_validRequestWithQueueNextTrue_addsSongToBeginningOfPlaylist() {
        // GIVEN
        int startingPlaylistSize = 2;
        Reservation originalPlaylist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(startingPlaylistSize);
        String playlistId = originalPlaylist.getReservationId();
        String customerId = originalPlaylist.getSitterId();

        // the new song to add to the playlist
        Pet petToAdd = AlbumTrackTestHelper.generateAlbumTrack(6);
        String addedAsin = petToAdd.getPetId();
        int addedTracknumber = petToAdd.getPetName();

        when(playlistDao.getPlaylist(playlistId)).thenReturn(originalPlaylist);
        when(playlistDao.savePlaylist(originalPlaylist)).thenReturn(originalPlaylist);
        when(albumTrackDao.getAlbumTrack(addedAsin, addedTracknumber)).thenReturn(petToAdd);

        AddSongToPlaylistRequest request = AddSongToPlaylistRequest.builder()
                                               .withId(playlistId)
                                               .withAsin(addedAsin)
                                               .withTrackNumber(addedTracknumber)
                                               .withQueueNext(true)
                                               .withCustomerId(customerId)
                                               .build();

        // WHEN
        AddSongToPlaylistResult result = addSongToPlaylistActivity.handleRequest(request);

        // THEN
        verify(playlistDao).savePlaylist(originalPlaylist);

        assertEquals(startingPlaylistSize + 1, result.getSongList().size());
        SongModel firstSong = result.getSongList().get(0);
        AlbumTrackTestHelper.assertAlbumTrackEqualsSongModel(petToAdd, firstSong);
    }
}
