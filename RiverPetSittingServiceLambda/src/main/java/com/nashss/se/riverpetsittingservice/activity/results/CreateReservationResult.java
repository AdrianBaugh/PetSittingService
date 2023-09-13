package com.nashss.se.riverpetsittingservice.activity.results;

import com.nashss.se.riverpetsittingservice.models.ReservationModel;

public class CreateReservationResult {
    private final ReservationModel reservation;

    private CreateReservationResult(ReservationModel reservation) {
        this.reservation = reservation;
    }

    public ReservationModel getReservation() {
        return reservation;
    }

    @Override
    public String toString() {
        return "CreateReservationResult{" +
                "reservation=" + reservation +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ReservationModel reservation;

        public Builder withReservation(ReservationModel reservation) {
            this.reservation = reservation;
            return this;
        }

        public CreateReservationResult build() {
            return new CreateReservationResult(reservation);
        }
    }
}
