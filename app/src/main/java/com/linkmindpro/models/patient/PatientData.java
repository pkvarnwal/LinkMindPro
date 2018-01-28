package com.linkmindpro.models.patient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientData implements Serializable {

    @SerializedName("patient_id") private String id;
    @SerializedName("ref_id") private String refId;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
