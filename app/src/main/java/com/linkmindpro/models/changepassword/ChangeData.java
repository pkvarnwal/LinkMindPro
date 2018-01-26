package com.linkmindpro.models.changepassword;

import com.google.gson.annotations.SerializedName;

public class ChangeData {

    @SerializedName("message") private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
