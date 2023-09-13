package com.nashss.se.riverpetsittingservice.activity.results;

import com.nashss.se.riverpetsittingservice.models.PetModel;

public class CreatePetResult {
    private final PetModel pet;

    private CreatePetResult(PetModel pet) {
        this.pet = pet;
    }

    public PetModel getPet() {
        return pet;
    }

    @Override
    public String toString() {
        return "CreatePetResult{" +
                "pet=" + pet +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PetModel pet;

        public Builder withPet(PetModel pet) {
            this.pet = pet;
            return this;
        }

        public CreatePetResult build() {
            return new CreatePetResult(pet);
        }
    }
}