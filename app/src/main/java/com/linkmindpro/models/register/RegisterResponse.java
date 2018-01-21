package com.linkmindpro.models.register;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

public class RegisterResponse {

    @SerializedName("status") private String status;
    @SerializedName("data") private RegisterData registerData;
    @SerializedName("error") private ErrorResponse errorResponse;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisterData getRegisterData() {
        return registerData;
    }

    public void setRegisterData(RegisterData registerData) {
        this.registerData = registerData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
