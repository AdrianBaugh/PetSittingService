package com.nashss.se.riverpetsittingservice.activity.results;

public class CancelReservationResult {
    private final String deleteResult;


    public CancelReservationResult(String deleteResult) {
        this.deleteResult = deleteResult;
    }

    public String getDeleteResult() {
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
        private String deleteResult;
        public Builder withDeleteResult(String deleteResult){
            this.deleteResult = deleteResult;
            return this;
        }
        public CancelReservationResult build(){
            return new CancelReservationResult(deleteResult);
        }
    }
}
