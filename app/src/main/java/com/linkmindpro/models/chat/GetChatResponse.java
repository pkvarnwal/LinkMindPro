package com.linkmindpro.models.chat;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

import java.util.ArrayList;

public class GetChatResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<ChatData> chatData = new ArrayList<>();
    @SerializedName("error")
    private ErrorResponse errorResponse;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ChatData> getChatData() {
        return chatData;
    }

    public void setChatData(ArrayList<ChatData> chatData) {
        this.chatData = chatData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
