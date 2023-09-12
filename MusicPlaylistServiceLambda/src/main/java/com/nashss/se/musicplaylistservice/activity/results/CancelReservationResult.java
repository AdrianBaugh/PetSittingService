package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

public class CancelReservationResult {
    private final ReservationModel reservation;


    public CancelReservationResult(ReservationModel reservation) {
        this.reservation = reservation;
    }

    public ReservationModel getReservation() {
        return reservation;
    }

    @Override
    public String toString() {
        return "CancelReservationResult{" +
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
        public CancelReservationResult build(){
            return new CancelReservationResult(reservation);
        }
    }
}
