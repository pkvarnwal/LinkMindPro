package com.linkmindpro.models.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("profession") private String profession;
    @SerializedName("image") private String image;
    @SerializedName("role") private String role;

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
}
