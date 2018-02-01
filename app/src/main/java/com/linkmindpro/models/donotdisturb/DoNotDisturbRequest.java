package com.linkmindpro.models.donotdisturb;

import com.google.gson.annotations.SerializedName;

public class DoNotDisturbRequest {

    @SerializedName("user_id") private String userId;
    @SerializedName("dnd") private String dnd;
    @SerializedName("message") private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDnd() {
        return dnd;
    }

    public void setDnd(String dnd) {
        this.dnd = dnd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
