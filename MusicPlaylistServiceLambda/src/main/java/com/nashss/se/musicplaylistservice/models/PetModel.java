package com.nashss.se.musicplaylistservice.models;

import java.util.Objects;

public class PetModel {
    private final String petId;
    private final String petName;
    private final String ownerId;

    private PetModel(String petId, String petName, String ownerId) {
        this.petId = petId;
        this.petName = petName;
        this.ownerId = ownerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetModel petModel = (PetModel) o;
        return  petId.equals(petModel.petId) &&
                petName.equals(petModel.petName) &&
                ownerId.equals(petModel.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, petName, ownerId);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String petId;
        private String petName;
        private String ownerId;

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
        public PetModel build() {
            return new PetModel(petId, petName, ownerId);
        }
    }
}
