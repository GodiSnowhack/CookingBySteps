package com.example.cookingbysteps.RegistrationLogin;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("Username")
    private String username;

    @SerializedName("Email")
    private String email;

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
