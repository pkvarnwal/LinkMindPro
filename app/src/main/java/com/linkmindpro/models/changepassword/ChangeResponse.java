package com.linkmindpro.models.changepassword;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

public class ChangeResponse {

    @SerializedName("status") private String status;
    @SerializedName("data") private ChangeData changeData;
    @SerializedName("error") private ErrorResponse errorResponse;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChangeData getChangeData() {
        return changeData;
    }

    public void setChangeData(ChangeData changeData) {
        this.changeData = changeData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
