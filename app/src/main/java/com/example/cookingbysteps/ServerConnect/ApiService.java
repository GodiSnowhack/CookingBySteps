package com.example.cookingbysteps.ServerConnect;

import com.example.cookingbysteps.RegistrationLogin.LoginRequest;
import com.example.cookingbysteps.RegistrationLogin.LoginResponse;
import com.example.cookingbysteps.RegistrationLogin.RegisterRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register")
    Call<ResponseBody> register(@Body RegisterRequest request);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
