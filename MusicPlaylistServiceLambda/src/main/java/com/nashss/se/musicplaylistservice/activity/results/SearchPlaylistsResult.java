package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

import java.util.ArrayList;
import java.util.List;

public class SearchPlaylistsResult {
    private final List<ReservationModel> playlists;

    private SearchPlaylistsResult(List<ReservationModel> playlists) {
        this.playlists = playlists;
    }

    public List<ReservationModel> getPlaylists() {
        return playlists;
    }

    @Override
    public String toString() {
        return "SearchPlaylistsResult{" +
                "playlists=" + playlists +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ReservationModel> playlists ;

        public Builder withPlaylists(List<ReservationModel> playlists) {
            this.playlists = new ArrayList<>(playlists);
            return this;
        }

        public SearchPlaylistsResult build() {
            return new SearchPlaylistsResult(playlists);
        }
    }
}
