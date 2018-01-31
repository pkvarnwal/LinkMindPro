package com.linkmindpro.models.patient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientData implements Serializable {

    @SerializedName("patient_id") private String id;
    @SerializedName("ref_id") private String refId;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("image") private String image;
    @SerializedName("dnd") private String dnd;
    @SerializedName("dnd_message") private String dndMessage;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDnd() {
        return dnd;
    }

    public void setDnd(String dnd) {
        this.dnd = dnd;
    }

    public String getDndMessage() {
        return dndMessage;
    }

    public void setDndMessage(String dndMessage) {
        this.dndMessage = dndMessage;
    }
}
