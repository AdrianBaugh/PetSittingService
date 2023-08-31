package com.nashss.se.musicplaylistservice.activity;

//import com.nashss.se.musicplaylistservice.activity.requests.GetPlaylistRequest;
//import com.nashss.se.musicplaylistservice.activity.results.GetPlaylistResult;
//import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
//import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
//
//import com.google.common.collect.Sets;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//public class GetReservationActivityTest {
//    @Mock
//    private PlaylistDao playlistDao;
//
//    private GetPlaylistActivity getPlaylistActivity;
//
//    @BeforeEach
//    public void setUp() {
//        initMocks(this);
//        getPlaylistActivity = new GetPlaylistActivity(playlistDao);
//    }
//
//    @Test
//    public void handleRequest_savedPlaylistFound_returnsPlaylistModelInResult() {
//        // GIVEN
//        String expectedId = "expectedId";
//        String expectedName = "expectedName";
//        String expectedCustomerId = "expectedCustomerId";
//        int expectedSongCount = 0;
//        List<String> expectedTags = List.of("tag");
//
//        Reservation playlist = new Reservation();
//        playlist.setReservationId(expectedId);
//        playlist.setPetOwnerId(expectedName);
//        playlist.setSitterId(expectedCustomerId);
//        playlist.setEndDate(expectedSongCount);
//        playlist.setTags(Sets.newHashSet(expectedTags));
//
//        when(playlistDao.getPlaylist(expectedId)).thenReturn(playlist);
//
//        GetPlaylistRequest request = GetPlaylistRequest.builder()
//            .withId(expectedId)
//            .build();
//
//        // WHEN
//        GetPlaylistResult result = getPlaylistActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(expectedId, result.getPlaylist().getId());
//        assertEquals(expectedName, result.getPlaylist().getName());
//        assertEquals(expectedCustomerId, result.getPlaylist().getCustomerId());
//        assertEquals(expectedSongCount, result.getPlaylist().getSongCount());
//        assertEquals(expectedTags, result.getPlaylist().getTags());
//    }
//}
