package com.nashss.se.musicplaylistservice.activity.requests;

public class GetAllPetsRequest {
    private final String ownerId;

    public GetAllPetsRequest(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public String toString() {
        return "GetAllPetsRequest{" +
                "ownerId='" + ownerId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String ownerId;
        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }
        public GetAllPetsRequest build() {
            return new GetAllPetsRequest(ownerId);
        }
    }
}
