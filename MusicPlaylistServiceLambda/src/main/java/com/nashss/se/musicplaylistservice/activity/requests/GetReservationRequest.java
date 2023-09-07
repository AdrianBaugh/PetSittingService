package com.nashss.se.musicplaylistservice.activity.requests;


public class GetReservationRequest {
     private final String reservationId;

    public GetReservationRequest(String reservationId) {
        this.reservationId = reservationId;
    }
    public String getReservationId(){
        return reservationId;
    }

    @Override
    public String toString() {
        return "GetReservationRequest{" +
                "reservationId='" + reservationId + '\'' +
                '}';
    }
    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String reservationId;
        public Builder withReservationId(String reservationId){
            this.reservationId = reservationId;
            return this;
        }
        public GetReservationRequest build() {
            return new GetReservationRequest(reservationId);
        }
    }
}
