package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;
import java.util.List;

@JsonDeserialize(builder = CreatePlaylistRequest.Builder.class)
public class CreateReservationRequest {

    private final String petOwnerId;
    private final String sitterId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<String> petList;

    private CreateReservationRequest(String petOwnerId, String sitterId, LocalDate startDate, LocalDate endDate, List<String> petList) {
        this.petOwnerId = petOwnerId;
        this.sitterId = sitterId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.petList = petList;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }

    public String getSitterId() {
        return sitterId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<String> getPetList() {
        return petList;
    }

    @Override
    public String toString() {
        return "CreateReservationRequest{" +
                "petOwnerId='" + petOwnerId + '\'' +
                ", sitterId='" + sitterId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", petList='" + petList +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String petOwnerId;
        private String sitterId;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<String> petList;

        public Builder withPetOwnerId(String petOwnerId) {
            this.petOwnerId = petOwnerId;
            return this;
        }

        public Builder withSitterId(String sitterId) {
            this.sitterId = sitterId;
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withPetList(List<String> petList) {
            this.petList = petList;
            return this;
        }

        public CreateReservationRequest build() {
            return new CreateReservationRequest(petOwnerId, sitterId, startDate, endDate, petList);
        }
    }
}
