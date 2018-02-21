package com.linkmindpro.models.login;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("profession") private String profession;
    @SerializedName("image") private String image;
    @SerializedName("role") private String role;
    @SerializedName("dnd") private String dnd;
    @SerializedName("dndmessage") private String dndmessage;
    @SerializedName("address") private String address;
    @SerializedName("state") private String state;
    @SerializedName("city") private String city;
    @SerializedName("zip") private String zip;
    @SerializedName("phone") private String phone;
    @SerializedName("reference_id") private String referenceId;
    private String completeAddress = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        if (role.equals("1")) return "Doctor";
        return "Patient";
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDnd() {
        return dnd;
    }

    public void setDnd(String dnd) {
        this.dnd = dnd;
    }

    public String getDndmessage() {
        return dndmessage;
    }

    public void setDndmessage(String dndmessage) {
        this.dndmessage = dndmessage;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompleteAddress() {
        completeAddress = "";
        if (!TextUtils.isEmpty(address)) {
            completeAddress = completeAddress + address;
        }
        if (!TextUtils.isEmpty(state)) {
            completeAddress = completeAddress + ", "+ state;
        }
        if (!TextUtils.isEmpty(city)) {
            completeAddress = completeAddress + ", "+ city;
        }
        if (!TextUtils.isEmpty(zip)) {
            completeAddress = completeAddress + ", "+ zip;
        }
        return completeAddress;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }
}
