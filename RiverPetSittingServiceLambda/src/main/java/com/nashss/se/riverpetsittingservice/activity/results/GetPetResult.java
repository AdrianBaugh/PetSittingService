package com.nashss.se.riverpetsittingservice.activity.results;

import com.nashss.se.riverpetsittingservice.models.PetModel;

public class GetPetResult {
    private final PetModel pet;

    private GetPetResult(PetModel pet) {
        this.pet = pet;
    }

    public PetModel getPet() {
        return pet;
    }

    @Override
    public String toString() {
        return "GetPetResult{" +
                "pet=" + pet +
                '}';
    }

    public static Builder builder() {return new Builder();}

    public static class Builder {
        private PetModel pet;

        public Builder withPet(PetModel pet) {
            this.pet = pet;
            return this;
        }

        public GetPetResult build() {return new GetPetResult(pet);}
    }
}
