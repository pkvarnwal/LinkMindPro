package com.linkmindpro.models.subscribe;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

public class SubsribeResponse {

    @SerializedName("data")
    private SubsribeData subsribeData;
    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private ErrorResponse errorResponse;

    public SubsribeData getSubsribeData() {
        return subsribeData;
    }

    public void setSubsribeData(SubsribeData subsribeData) {
        this.subsribeData = subsribeData;
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
