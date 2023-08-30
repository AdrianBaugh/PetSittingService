package com.nashss.se.musicplaylistservice.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.nashss.se.musicplaylistservice.utils.CollectionUtils.copyToList;

public class ReservationModel {
    private String reservationId;
    private String petOwnerId;
    private String sitterId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<String> petList;

    public ReservationModel(String reservationId, String petOwnerId, String sitterId, LocalDate startDate,
                            LocalDate endDate, String status, List<String> petList) {
        this.reservationId = reservationId;
        this.petOwnerId = petOwnerId;
        this.sitterId = sitterId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.petList = petList;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }

    public String getSitterId() {
        return sitterId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getPetList() {
        return copyToList(petList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationModel that = (ReservationModel) o;
        return Objects.equals(reservationId, that.reservationId) && Objects.equals(petOwnerId, that.petOwnerId) &&
                Objects.equals(sitterId, that.sitterId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate)
                && Objects.equals(status, that.status) && Objects.equals(petList, that.petList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, petOwnerId, sitterId, startDate, endDate, status, petList);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String reservationId;
        private String petOwnerId;
        private String sitterId;
        private LocalDate startDate;
        private LocalDate endDate;
        private String status;
        private List<String> petList;

        public Builder withReservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public Builder withPetOwnerId(String petOwnerId) {
            this.petOwnerId = petOwnerId;
            return this;
        }

        public Builder withSitterId(String sitterId) {
            this.sitterId = sitterId;
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withPetList(List<String> petList) {
            this.petList = copyToList(petList);
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ReservationModel build() {
            return new ReservationModel(reservationId, petOwnerId, sitterId, startDate, endDate, status, petList);
        }
    }
}
