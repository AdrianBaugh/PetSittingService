package com.nashss.se.musicplaylistservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import com.nashss.se.musicplaylistservice.converters.LocalDateConverter;

import java.time.LocalDate;

import java.util.List;
import java.util.Objects;


/**
 * Represents a record in the playlists table.
 */
@DynamoDBTable(tableName = "reservations")
public class Reservation {
    private String reservationId;
    private String petOwnerId;
    private String sitterId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<String> petList;

    @DynamoDBRangeKey(attributeName = "reservationId")
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    // "name" is a reserved word in DDB, so the attribute in the table is called "playlistName".
    @DynamoDBHashKey(attributeName = "ownerId")
    public String getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(String petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    @DynamoDBAttribute(attributeName = "sitterId")
    public String getSitterId() {
        return sitterId;
    }

    public void setSitterId(String sitterId) {
        this.sitterId = sitterId;
    }

    @DynamoDBAttribute(attributeName = "startDate")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @DynamoDBAttribute(attributeName = "endDate")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    // PARTICIPANTS: You do not need to modify the songList getters/setters or annotations
    @DynamoDBAttribute(attributeName = "petList")
    public List<String> getPetList() {
        return petList;
    }

    public void setPetList(List<String> petList) {
        this.petList = petList;
    }

    @DynamoDBAttribute(attributeName = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        return reservationId.equals(reservation.reservationId) &&
                petOwnerId.equals(reservation.petOwnerId) &&
                sitterId.equals(reservation.sitterId) &&
                startDate.equals(reservation.startDate) &&
                endDate.equals(reservation.endDate) &&
                status.equals(reservation.status) &&
                Objects.equals(petList, reservation.petList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, petOwnerId, sitterId, startDate, endDate, status, petList);
    }

}
