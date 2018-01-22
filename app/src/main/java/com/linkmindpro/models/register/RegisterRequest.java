package com.linkmindpro.models.register;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterRequest implements Serializable {

    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;
    @SerializedName("role") private String role;
    @SerializedName("address") private String address;
    @SerializedName("state") private String state;
    @SerializedName("city") private String city;
    @SerializedName("zipcode") private String zipcode;
    @SerializedName("phone") private String phone;
    @SerializedName("dob") private String dob;

    @SerializedName("pharmacy_name") private String pharmacyName;
    @SerializedName("pharmacy_address") private String pharmacyAddress;
    @SerializedName("pharmacy_state") private String pharmacyState;
    @SerializedName("pharmacy_city") private String pharmacyCity;
    @SerializedName("pharmacy_zipcode") private String pharmacyZipcode;
    @SerializedName("pharmacy_phone") private String pharmacyPhone;

    @SerializedName("insurance_name") private String insuranceName;
    @SerializedName("insurance_id") private String insuranceId;
    @SerializedName("insurance_group") private String insuranceGroup;
    @SerializedName("insurance_state") private String insuranceState;
    @SerializedName("insurance_zipcode") private String insuranceZipcode;
    @SerializedName("insurance_phone") private String insurancePhone;

    @SerializedName("profession") private String profession;


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getInsuranceGroup() {
        return insuranceGroup;
    }

    public void setInsuranceGroup(String insuranceGroup) {
        this.insuranceGroup = insuranceGroup;
    }

    public String getInsuranceState() {
        return insuranceState;
    }

    public void setInsuranceState(String insuranceState) {
        this.insuranceState = insuranceState;
    }

    public String getInsuranceZipcode() {
        return insuranceZipcode;
    }

    public void setInsuranceZipcode(String insuranceZipcode) {
        this.insuranceZipcode = insuranceZipcode;
    }

    public String getInsurancePhone() {
        return insurancePhone;
    }

    public void setInsurancePhone(String insurancePhone) {
        this.insurancePhone = insurancePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyState() {
        return pharmacyState;
    }

    public void setPharmacyState(String pharmacyState) {
        this.pharmacyState = pharmacyState;
    }

    public String getPharmacyCity() {
        return pharmacyCity;
    }

    public void setPharmacyCity(String pharmacyCity) {
        this.pharmacyCity = pharmacyCity;
    }

    public String getPharmacyZipcode() {
        return pharmacyZipcode;
    }

    public void setPharmacyZipcode(String pharmacyZipcode) {
        this.pharmacyZipcode = pharmacyZipcode;
    }

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }
}
