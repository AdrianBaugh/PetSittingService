package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.CreatePlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreatePlaylistResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.musicplaylistservice.models.ReservationModel;

import com.nashss.se.projectresources.music.playlist.servic.util.MusicPlaylistServiceUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;

/**
 * Implementation of the CreatePlaylistActivity for the MusicPlaylistService's CreatePlaylist API.
 * <p>
 * This API allows the customer to create a new playlist with no songs.
 */
public class CreatePlaylistActivity {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;

    /**
     * Instantiates a new CreatePlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlists table.
     */
    @Inject
    public CreatePlaylistActivity(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    /**
     * This method handles the incoming request by persisting a new playlist
     * with the provided playlist name and customer ID from the request.
     * <p>
     * It then returns the newly created playlist.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createPlaylistRequest request object containing the playlist name and customer ID
     *                              associated with it
     * @return createPlaylistResult result object containing the API defined {@link ReservationModel}
     */
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest) {
//        log.info("Received CreatePlaylistRequest {}", createPlaylistRequest);
//
//        if (!MusicPlaylistServiceUtils.isValidString(createPlaylistRequest.getName())) {
//            throw new InvalidAttributeValueException("Reservation name [" + createPlaylistRequest.getName() +
//                    "] contains illegal characters");
//        }
//
//        if (!MusicPlaylistServiceUtils.isValidString(createPlaylistRequest.getCustomerId())) {
//            throw new InvalidAttributeValueException("Reservation customer ID [" + createPlaylistRequest.getCustomerId() +
//                    "] contains illegal characters");
//        }
//
//        Set<String> playlistTags = null;
//        if (createPlaylistRequest.getTags() != null) {
//            playlistTags = new HashSet<>(createPlaylistRequest.getTags());
//        }
//
//        Reservation newPlaylist = new Reservation();
//        newPlaylist.setReservationId(MusicPlaylistServiceUtils.generatePlaylistId());
//        newPlaylist.setPetOwnerId(createPlaylistRequest.getName());
//        newPlaylist.setSitterId(createPlaylistRequest.getCustomerId());
//        newPlaylist.setStartDate(createPlaylistRequest.getCustomerName());
//        newPlaylist.setEndDate(0);
//        newPlaylist.setTags(playlistTags);
//        newPlaylist.setPetList(new ArrayList<>());
//
//        playlistDao.savePlaylist(newPlaylist);
//
//        ReservationModel playlistModel = new ModelConverter().toReservationModel(newPlaylist);
//        return CreatePlaylistResult.builder()
//                .withPlaylist(playlistModel)
//                .build();
        return null;
    }
}
