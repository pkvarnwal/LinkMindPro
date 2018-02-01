package com.linkmindpro.models.donotdisturb;


import com.google.gson.annotations.SerializedName;

public class DoNotDisturbData {

    @SerializedName("message") private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
