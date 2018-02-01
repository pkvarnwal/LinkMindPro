package com.linkmindpro.models.donotdisturb;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

public class DoNotDisturbResponse {

    @SerializedName("status") private String status;
    @SerializedName("data") private DoNotDisturbData doNotDisturbData;
    @SerializedName("error") private ErrorResponse errorResponse;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DoNotDisturbData getDoNotDisturbData() {
        return doNotDisturbData;
    }

    public void setDoNotDisturbData(DoNotDisturbData doNotDisturbData) {
        this.doNotDisturbData = doNotDisturbData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
