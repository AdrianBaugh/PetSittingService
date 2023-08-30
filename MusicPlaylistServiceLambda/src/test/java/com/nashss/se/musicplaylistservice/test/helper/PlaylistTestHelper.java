package com.nashss.se.musicplaylistservice.test.helper;

import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;
import com.nashss.se.musicplaylistservice.dynamodb.models.Reservation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class PlaylistTestHelper {
    private PlaylistTestHelper() {
    }

    public static Reservation generatePlaylist() {
        return generatePlaylistWithNAlbumTracks(1);
    }

    public static Reservation generatePlaylistWithNAlbumTracks(int numTracks) {
        Reservation playlist = new Reservation();
        playlist.setReservationId("id");
        playlist.setPetOwnerId("a playlist");
        playlist.setSitterId("CustomerABC");
        playlist.setTags(Collections.singleton("tag"));

        List<Pet> pets = new LinkedList<>();
        for (int i = 0; i < numTracks; i++) {
            pets.add(AlbumTrackTestHelper.generateAlbumTrack(i));
        }
        playlist.setPetList(pets);
        playlist.setEndDate(pets.size());

        return playlist;
    }
}
