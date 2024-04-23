package com.example.cookingbysteps.RegistrationLogin;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Username")
    private String username;

    @SerializedName("Email")
    private String email;

    @SerializedName("UserID")
    private Integer userID;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Integer getUserId() {
        return userID;
    }
}
