package com.linkmindpro.models.subscribe;


import com.google.gson.annotations.SerializedName;

public class SubsribeRequest {

    @SerializedName("email") private String email;
    @SerializedName("id") private String id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
