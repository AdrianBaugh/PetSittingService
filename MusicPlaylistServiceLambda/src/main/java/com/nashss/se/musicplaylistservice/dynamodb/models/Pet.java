package com.nashss.se.musicplaylistservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

/**
 * Represents a Pet in the Pets table.
 */
@DynamoDBTable(tableName = "pets")
public class Pet {
    private String petId;
    private String petName;
    private String ownerId;

    private static final String GSI_INDEX_NAME = "PetsByOwnerIndex";
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
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_INDEX_NAME)
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
