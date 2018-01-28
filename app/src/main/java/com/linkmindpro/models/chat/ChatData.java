package com.linkmindpro.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatData {

    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("reciver_id")
    @Expose
    private String reciverId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
