package com.nashss.se.musicplaylistservice.activity.requests;


public class GetReservationRequest {
     private final String reservationId;
     private final String ownerId;

    public GetReservationRequest(String reservationId, String ownerId) {
        this.reservationId = reservationId;
        this.ownerId = ownerId;
    }
    public String getReservationId(){
        return reservationId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public String toString() {
        return "GetReservationRequest{" +
                "reservationId='" + reservationId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String reservationId;
        private String ownerId;

        public Builder withReservationId(String reservationId){
            this.reservationId = reservationId;
            return this;
        }

        public Builder withOwnerId(String ownerId){
            this.ownerId = ownerId;
            return this;
        }
        public GetReservationRequest build() {
            return new GetReservationRequest(reservationId, ownerId);
        }
    }
}
