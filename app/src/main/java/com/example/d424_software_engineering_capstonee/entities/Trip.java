package com.example.d424_software_engineering_capstonee.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
//Create Trip entity
@Entity(tableName = "trips")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    private int tripID;
    private String tripName;
    private double price;
    private String hotel;
    private String startDate;
    private String endDate;

    public Trip(double price, String tripName, String hotel, String startDate, String endDate){
        this.price = price;
        this.tripName = tripName;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTripID(){return tripID;}

    public void setTripID(int tripID){
        this.tripID = tripID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
