package com.linkmindpro.models.patient;

import com.google.gson.annotations.SerializedName;

public class PatientRequest {

    @SerializedName("user_id") private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
