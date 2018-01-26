package com.linkmindpro.models.changepassword;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangeRequest implements Serializable {

    @SerializedName("user_id") private String userId;
    @SerializedName("oldpassword") private String oldpassword;
    @SerializedName("password") private String password;
    @SerializedName("cpassword") private String cpassword;

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
