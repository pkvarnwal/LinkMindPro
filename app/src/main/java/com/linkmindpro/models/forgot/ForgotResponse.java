package com.linkmindpro.models.forgot;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

public class ForgotResponse {

    @SerializedName("data") private ForgotData forgotData;
    @SerializedName("status") private String status;
    @SerializedName("error") private ErrorResponse errorResponse;

    public ForgotData getForgotData() {
        return forgotData;
    }

    public void setForgotData(ForgotData forgotData) {
        this.forgotData = forgotData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
