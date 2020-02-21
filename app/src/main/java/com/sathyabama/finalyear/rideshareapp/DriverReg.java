package com.sathyabama.finalyear.rideshareapp;


public class DriverReg {
    public String fullName;
    public String email;
    public String password;
    public String mobile;
    public String age;
    public String regNo;
    public String maker;
    public String acStatus;
//    public String lat;
//    public String lon;
//    public String rating;
    public String licNo;
    public String aadharNum;
    public String noOfSeats;
    public String gender;
    public String upiId;

    public DriverReg() {
    }

    public DriverReg(String fullName, String email, String password, String mobile, String age, String regNo, String maker, String acStatus, String licNo, String aadharNum, String noOfSeats, String gender, String upiId) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.age = age;
        this.regNo = regNo;
        this.maker = maker;
        this.acStatus = acStatus;
        this.licNo = licNo;
        this.aadharNum = aadharNum;
        this.noOfSeats = noOfSeats;
        this.gender = gender;
        this.upiId = upiId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public String getLicNo() {
        return licNo;
    }

    public void setLicNo(String licNo) {
        this.licNo = licNo;
    }

    public String getAadharNum() {
        return aadharNum;
    }

    public void setAadharNum(String aadharNum) {
        this.aadharNum = aadharNum;
    }

    public String getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(String noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}

