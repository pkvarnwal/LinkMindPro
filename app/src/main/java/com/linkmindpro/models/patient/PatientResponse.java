package com.linkmindpro.models.patient;

import com.google.gson.annotations.SerializedName;
import com.linkmindpro.models.error.ErrorResponse;

import java.util.ArrayList;

public class PatientResponse {

    @SerializedName("status") private String status;
    @SerializedName("data") private ArrayList<PatientData> patientData = new ArrayList<>();
    @SerializedName("error") private ErrorResponse errorResponse;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PatientData> getPatientData() {
        return patientData;
    }

    public void setPatientData(ArrayList<PatientData> patientData) {
        this.patientData = patientData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
