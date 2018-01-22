package com.linkmindpro.models.login;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

import java.io.Serializable;

public class LoginResponse implements Serializable {

   @SerializedName("data") private LoginData loginData;
   @SerializedName("status") private String status;
    @SerializedName("error") private ErrorResponse errorResponse;

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
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
