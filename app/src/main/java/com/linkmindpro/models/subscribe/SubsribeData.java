package com.linkmindpro.models.subscribe;

import com.google.gson.annotations.SerializedName;

public class SubsribeData {

    @SerializedName("message") private String message;
    @SerializedName("code") private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
