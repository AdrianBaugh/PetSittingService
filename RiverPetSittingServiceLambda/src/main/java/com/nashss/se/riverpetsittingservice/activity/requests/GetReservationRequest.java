package com.nashss.se.riverpetsittingservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetReservationRequest.Builder.class)
public class GetReservationRequest {
     private final String reservationId;
     private final String petOwnerId;

    public GetReservationRequest(String reservationId, String petOwnerId) {
        this.reservationId = reservationId;
        this.petOwnerId = petOwnerId;
    }
    public String getReservationId(){
        return reservationId;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }


    public static Builder builder(){
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder{
        private String reservationId;
        private String petOwnerId;

        public Builder withReservationId(String reservationId){
            this.reservationId = reservationId;
            return this;
        }

        public Builder withOwnerId(String ownerId){
            this.petOwnerId = ownerId;
            return this;
        }
        public GetReservationRequest build() {
            return new GetReservationRequest(reservationId, petOwnerId);
        }
    }
}
