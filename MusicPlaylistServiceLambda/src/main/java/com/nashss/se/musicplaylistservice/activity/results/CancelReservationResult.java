package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ReservationModel;

public class CancelReservationResult {
    private final Boolean deleteResult;


    public CancelReservationResult(Boolean deleteResult) {
        this.deleteResult = deleteResult;
    }

    public Boolean getDeleteResult() {
        return deleteResult;
    }

    @Override
    public String toString() {
        return "CancelReservationResult{" +
                "result=" + deleteResult +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private Boolean deleteResult;
        public Builder withDeleteResult(Boolean deleteResult){
            this.deleteResult = deleteResult;
            return this;
        }
        public CancelReservationResult build(){
            return new CancelReservationResult(deleteResult);
        }
    }
}
