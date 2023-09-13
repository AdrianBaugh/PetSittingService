package com.nashss.se.riverpetsittingservice.activity.requests;

public class CancelReservationRequest {

    private final String reservationId;
    private final String petOwnerId;

    public CancelReservationRequest(String reservationId, String petOwnerId) {
        this.reservationId = reservationId;
        this.petOwnerId = petOwnerId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private String reservationId;
        private String petOwnerId;

        public Builder withReservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.petOwnerId = ownerId;
            return this;
        }

        public CancelReservationRequest build() {
            return new CancelReservationRequest(reservationId, petOwnerId);
        }
    }
}
