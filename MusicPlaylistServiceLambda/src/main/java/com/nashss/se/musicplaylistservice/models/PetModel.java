package com.nashss.se.musicplaylistservice.models;

import java.util.Objects;

public class PetModel {
    private final String petId;
    private final String petName;
    private final String ownerId;
    private final String ownerName;

    private PetModel(String petId, String petName, String ownerId, String ownerName) {
        this.petId = petId;
        this.petName = petName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public String getPetId() {
        return petId;
    }

    public String getPetName() {
        return petName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        PetModel petModel = (PetModel) other;
        return Objects.equals(petId, petModel.petId) &&
                Objects.equals(petName, petModel.petName) &&
                Objects.equals(ownerId, petModel.ownerId) &&
                Objects.equals(ownerName, petModel.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, petName, ownerId, ownerName);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String petId;
        private String petName;
        private String ownerId;
        private String ownerName;

        public Builder withPetId(String petId) {
            this.petId = petId;
            return this;
        }

        public Builder withPetName(String petName) {
            this.petName = petName;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder withOwnerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }
        public PetModel build() {
            return new PetModel(petId, petName, ownerId, ownerName);
        }
    }
}
