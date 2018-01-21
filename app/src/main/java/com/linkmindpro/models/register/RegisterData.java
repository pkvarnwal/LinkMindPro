package com.linkmindpro.models.register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

    @SerializedName("message") private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
