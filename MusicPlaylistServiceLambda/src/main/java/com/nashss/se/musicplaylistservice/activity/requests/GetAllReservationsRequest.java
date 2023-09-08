package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@JsonDeserialize(builder = GetAllReservationsRequest.Builder.class)
public class GetAllReservationsRequest {
    private final String petOwnerId;

    public GetAllReservationsRequest(String petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }

    @Override
    public String toString() {
        return "GetAllReservationsRequest{" +
                "petOwnerId='" + petOwnerId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
   }

    @JsonPOJOBuilder
    public static class Builder{
        private String petOwnerId;

        public Builder withPetOwnerId(String petOwnerId){
            this.petOwnerId = petOwnerId;
            return this;
        }

        public GetAllReservationsRequest build() {
            return new GetAllReservationsRequest(petOwnerId);
        }
    }
}
