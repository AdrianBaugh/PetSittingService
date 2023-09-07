package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

public class GetReservationResult {

    private final ReservationModel reservation;


    public GetReservationResult(ReservationModel reservation) {
        this.reservation = reservation;
    }

    public ReservationModel getReservation() {
        return reservation;
    }

    @Override
    public String toString() {
        return "GetReservationResult{" +
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
        public GetReservationResult build(){
            return new GetReservationResult(reservation);
        }
    }
}
