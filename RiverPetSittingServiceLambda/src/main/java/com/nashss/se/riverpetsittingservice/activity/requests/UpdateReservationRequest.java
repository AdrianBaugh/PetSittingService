package com.nashss.se.riverpetsittingservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateReservationRequest.Builder.class)
public class UpdateReservationRequest {
    private final String petOwnerId;
    private final String reservationId;
    private final String startDate;
    private final String endDate;

    public UpdateReservationRequest(String petOwnerId, String reservationId, String startDate, String endDate) {
        this.petOwnerId = petOwnerId;
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }
    public String getReservationId() {
        return reservationId;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    @Override
    public String toString() {
        return "UpdateReservationRequest{" +
                "petOwnerId='" + petOwnerId + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String petOwnerId;
        private String reservationId;
        private String startDate;
        private String endDate;

        public Builder withPetOwnerId(String petOwnerId) {
            this.petOwnerId = petOwnerId;
            return this;
        }
        public Builder withReservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }
        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public UpdateReservationRequest build() {
            return new UpdateReservationRequest(petOwnerId, reservationId, startDate, endDate);
        }
    }
}
