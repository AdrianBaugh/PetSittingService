package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.PetModel;

import java.util.ArrayList;
import java.util.List;

public class AddSongToPlaylistResult {
    private final List<PetModel> songList;

    private AddSongToPlaylistResult(List<PetModel> songList) {
        this.songList = songList;
    }

    public List<PetModel> getSongList() {
        return new ArrayList<>(songList);
    }

    @Override
    public String toString() {
        return "AddSongToPlaylistResult{" +
                "songList=" + songList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<PetModel> songList;

        public Builder withSongList(List<PetModel> songList) {
            this.songList = new ArrayList<>(songList);
            return this;
        }

        public AddSongToPlaylistResult build() {
            return new AddSongToPlaylistResult(songList);
        }
    }
}
