package com.nashss.se.musicplaylistservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

/**
 * Represents a record in the album_tracks table.
 */
@DynamoDBTable(tableName = "pets")
public class Pet {
    private String petId;
    private String petName;
    private String ownerId;


    @DynamoDBHashKey(attributeName = "petId")
    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    @DynamoDBAttribute(attributeName = "petName")
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    @DynamoDBAttribute(attributeName = "ownerId")
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet that = (Pet) o;
        return petId.equals(that.petId) &&
               petName.equals(that.petName) &&
               Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, petName, ownerId);
    }

    @Override
    public String toString() {
        return "Pet{" +
               "petId='" + petId + '\'' +
               ", petName=" + petName +
               ", ownerId='" + ownerId + '\'' +
               '}';
    }
}
