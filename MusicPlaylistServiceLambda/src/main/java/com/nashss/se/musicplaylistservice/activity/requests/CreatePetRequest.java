package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreatePetRequest.Builder.class)
public class CreatePetRequest {

    private final String petName;
    private final String ownerId;

    public CreatePetRequest(String petName, String ownerId) {
        this.petName = petName;
        this.ownerId = ownerId;
    }

    public String getPetName() {
        return petName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public String toString() {
        return "CreatePetRequest{" +
                "petName='" + petName + '\'' +
                ", ownerId='" + ownerId + '\'';}

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String petName;
        private String ownerId;

        public Builder withPetName(String petName) {
            this.petName = petName;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public CreatePetRequest build() {
            return new CreatePetRequest(petName, ownerId);
        }
    }
}
