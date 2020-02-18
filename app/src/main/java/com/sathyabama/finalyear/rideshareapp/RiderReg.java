package com.sathyabama.finalyear.rideshareapp;


public class RiderReg {
    public String fullName;
    public String password;
    public String mobile;
    public String email;
    public String gender;
    public String aadharNumber;
    public String emergencyNumber;

    public RiderReg() {
    }

    public RiderReg(String fullName, String password, String mobile, String email, String gender, String aadharNumber, String emergencyNumber) {
        this.fullName = fullName;
        this.password = password;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.aadharNumber = aadharNumber;
        this.emergencyNumber = emergencyNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
}

