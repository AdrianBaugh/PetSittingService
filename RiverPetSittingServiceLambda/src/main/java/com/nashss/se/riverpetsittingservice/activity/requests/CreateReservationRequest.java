<<<<<<< HEAD:MusicPlaylistServiceLambda/src/main/java/com/nashss/se/musicplaylistservice/activity/requests/CreateReservationRequest.java
package com.nashss.se.musicplaylistservice.activity.requests;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
=======
package com.nashss.se.riverpetsittingservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

>>>>>>> main:RiverPetSittingServiceLambda/src/main/java/com/nashss/se/riverpetsittingservice/activity/requests/CreateReservationRequest.java
import java.util.List;

@JsonDeserialize(builder = CreateReservationRequest.Builder.class)
public class CreateReservationRequest {

    private final String petOwnerId;
    private final String startDate;
    private final String endDate;
    private final List<String> petList;

    private CreateReservationRequest(String petOwnerId, String startDate, String endDate, List<String> petList) {
        this.petOwnerId = petOwnerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.petList = petList;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }


    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<String> getPetList() {
        return petList;
    }

    @Override
    public String toString() {
        return "CreateReservationRequest{" +
                "petOwnerId='" + petOwnerId + '\'' +
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
        private String startDate;
        private String endDate;
        private List<String> petList;

        public Builder withPetOwnerId(String petOwnerId) {
            this.petOwnerId = petOwnerId;
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

        public Builder withPetList(List<String> petList) {
            this.petList = petList;
            return this;
        }

        public CreateReservationRequest build() {
            return new CreateReservationRequest(petOwnerId, startDate, endDate, petList);
        }
    }
}
