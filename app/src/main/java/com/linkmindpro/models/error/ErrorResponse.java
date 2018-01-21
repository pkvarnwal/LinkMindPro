package com.linkmindpro.models.error;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("message") private String errorMessage;
    @SerializedName("code") private String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
