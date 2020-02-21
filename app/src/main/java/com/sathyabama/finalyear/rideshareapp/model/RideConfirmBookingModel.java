package com.sathyabama.finalyear.rideshareapp.model;

public class RideConfirmBookingModel {
    private String riderName;
    private Double currentLat;
    private Double currentLong;
    private Double destLat;
    private Double destLong;
    private String phoneNum;
    private String time;
    private String price;
    private String upiID;
    private String driverName;

    public RideConfirmBookingModel() {
    }

    public RideConfirmBookingModel(String riderName, Double currentLat, Double currentLong, Double destLat, Double destLong, String phoneNum, String time, String price, String upiID, String driverName) {
        this.riderName = riderName;
        this.currentLat = currentLat;
        this.currentLong = currentLong;
        this.destLat = destLat;
        this.destLong = destLong;
        this.phoneNum = phoneNum;
        this.time = time;
        this.price = price;
        this.upiID = upiID;
        this.driverName = driverName;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpiID() {
        return upiID;
    }

    public void setUpiID(String upiID) {
        this.upiID = upiID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
