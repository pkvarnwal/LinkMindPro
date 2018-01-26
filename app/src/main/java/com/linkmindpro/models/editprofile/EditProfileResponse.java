package com.linkmindpro.models.editprofile;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

import java.util.ArrayList;

public class EditProfileResponse {

    @SerializedName("status") private String status;
    @SerializedName("data") private EditProfileData editProfileData;
    @SerializedName("error") private ErrorResponse errorResponse;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EditProfileData getEditProfileData() {
        return editProfileData;
    }

    public void setEditProfileData(EditProfileData editProfileData) {
        this.editProfileData = editProfileData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
