package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

public class GetReservationResponse {

    private final ReservationModel reservation;


    public GetReservationResponse(ReservationModel reservation) {
        this.reservation = reservation;
    }

    public ReservationModel getReservation() {
        return reservation;
    }

    @Override
    public String toString() {
        return "GetReservationResponse{" +
                "reservation=" + reservation +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private ReservationModel reservation;
        public Builder withReservation(ReservationModel reservation){
            this.reservation = reservation;
            return this;
        }
        public GetReservationResponse build(){
            return new GetReservationResponse(reservation);
        }
    }
}
