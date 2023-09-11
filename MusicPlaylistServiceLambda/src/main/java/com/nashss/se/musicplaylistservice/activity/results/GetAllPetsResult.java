package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.PetModel;

import java.util.List;

public class GetAllPetsResult {
    private final List<PetModel> pets;

    private GetAllPetsResult(List<PetModel> pets) {
        this.pets = pets;
    }
    public List<PetModel> getPets() {
        return pets;
    }
    @Override
    public String toString() {
        return "GetAllPetsResult{" +
                "pets=" + pets +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<PetModel> pets;
        public Builder withPets(List<PetModel> pets) {
            this.pets = pets;
            return this;
        }

        public GetAllPetsResult build() {
            return new GetAllPetsResult(pets);
        }
    }
}
