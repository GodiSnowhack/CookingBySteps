package com.example.cookingbysteps;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("Username")
    private String username;

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password;

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
