package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

public class GetPlaylistResult {
    private final ReservationModel playlist;

    private GetPlaylistResult(ReservationModel playlist) {
        this.playlist = playlist;
    }

    public ReservationModel getPlaylist() {
        return playlist;
    }

    @Override
    public String toString() {
        return "GetPlaylistResult{" +
                "playlist=" + playlist +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ReservationModel playlist;

        public Builder withPlaylist(ReservationModel playlist) {
            this.playlist = playlist;
            return this;
        }

        public GetPlaylistResult build() {
            return new GetPlaylistResult(playlist);
        }
    }
}
