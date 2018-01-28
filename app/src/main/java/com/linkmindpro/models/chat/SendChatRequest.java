package com.linkmindpro.models.chat;

import com.google.gson.annotations.SerializedName;

public class SendChatRequest {

    @SerializedName("sender_id") private String senderId;
    @SerializedName("reciever_id") private String recieverId;
    @SerializedName("message") private String message;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
