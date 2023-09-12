package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllReservationsResult {
    private final List<ReservationModel> reservationList;

    public GetAllReservationsResult(List<ReservationModel> reservationList) {
        this.reservationList = reservationList;
    }

    public List<ReservationModel> getReservationList() {
        return reservationList;
    }

    @Override
    public String toString() {
        return "GetAllReservationsResult{" +
                "reservationList=" + reservationList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<ReservationModel> reservationList;

        public Builder withReservationList(List<ReservationModel> reservationList) {
            this.reservationList = new ArrayList<>(reservationList);
            return this;
        }
        public GetAllReservationsResult build() { return new GetAllReservationsResult(reservationList);}
    }
}
