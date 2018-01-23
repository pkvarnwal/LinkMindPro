package com.linkmindpro.models.forgot;

import com.google.gson.annotations.SerializedName;

public class ForgotData {

    @SerializedName("message") private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
