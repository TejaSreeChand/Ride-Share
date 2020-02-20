package com.sathyabama.finalyear.rideshareapp.model;

public class RideBookingModel {
    private String fullName;
    private Double currentLat;
    private Double currentLong;
    private Double destLat;
    private Double destLong;
    private String phoneNum;
    private String time;

    public RideBookingModel(String fullName, Double currentLat, Double currentLong, Double destLat, Double destLong, String phoneNum, String time) {
        this.fullName = fullName;
        this.currentLat = currentLat;
        this.currentLong = currentLong;
        this.destLat = destLat;
        this.destLong = destLong;
        this.phoneNum = phoneNum;
        this.time = time;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(Double currentLat) {
        this.currentLat = currentLat;
    }

    public Double getCurrentLong() {
        return currentLong;
    }

    public void setCurrentLong(Double currentLong) {
        this.currentLong = currentLong;
    }

    public Double getDestLat() {
        return destLat;
    }

    public void setDestLat(Double destLat) {
        this.destLat = destLat;
    }

    public Double getDestLong() {
        return destLong;
    }

    public void setDestLong(Double destLong) {
        this.destLong = destLong;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
