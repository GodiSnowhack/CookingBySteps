package com.example.cookingbysteps.RegistrationLogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingbysteps.CreateRecipe.CreateRecipePage;
import com.example.cookingbysteps.MainActivity.MainActivity;
import com.example.cookingbysteps.NavigationManager;
import com.example.cookingbysteps.R;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;
import com.google.android.material.navigation.NavigationView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText UsernameEditRegis, EmailEditRegis, PasswordEditRegis, EmailEditLogin, PasswordEditLogin;

    View RegisLayout, LoginLayout;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        NavigationView myNavView = findViewById(R.id.nav_view_id);
        View headerView = myNavView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.UsernameText);

        NavigationManager.setupNavigation(myNavView, this, navUsername);

        RegisLayout = findViewById(R.id.RegisLayout);
        LoginLayout = findViewById(R.id.LoginLayout);

        Button changeMode = findViewById(R.id.ChangeMode);
        changeMode.setOnClickListener(v -> {
            if (RegisLayout.getVisibility() == View.VISIBLE) {
                RegisLayout.setVisibility(View.GONE);
                LoginLayout.setVisibility(View.VISIBLE);
            } else { // Иначе, если LoginLayout видим, то скрываем его и показываем RegisLayout
                RegisLayout.setVisibility(View.VISIBLE);
                LoginLayout.setVisibility(View.GONE);
            }
        });

        UsernameEditRegis = findViewById(R.id.UsernameEditRegis);
        EmailEditRegis = findViewById(R.id.EmailEditRegis);
        PasswordEditRegis = findViewById(R.id.PasswordEditRegis);
        Button regisButton = findViewById(R.id.RegisButton);

        regisButton.setOnClickListener(v -> register());

        EmailEditLogin = findViewById(R.id.EmailEditLogin);
        PasswordEditLogin = findViewById(R.id.PasswordEditLogin);
        Button loginButton = findViewById(R.id.LoginButton);

        loginButton.setOnClickListener(v -> login());
    }

    private void login() {
        String email = EmailEditLogin.getText().toString();
        String password = PasswordEditLogin.getText().toString();

        LoginRequest request = new LoginRequest(email, password);

        ApiService service = ApiClient.getClient();
        Call<LoginResponse> call = service.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        // Сохраняем имя пользователя, электронную почту и userID в SharedPreferences
                        saveUserData(loginResponse.getUsername(), loginResponse.getEmail(), loginResponse.getUserId());

                        Toast.makeText(RegistrationActivity.this, "Login successful. Welcome, " + loginResponse.getUsername(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData(String username, String email, int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putInt("userID", userId); // исправлено на "userID"
        editor.apply();
    }



    private void register() {
        String username = UsernameEditRegis.getText().toString();
        String email = EmailEditRegis.getText().toString();
        String password = PasswordEditRegis.getText().toString();

        RegisterRequest request = new RegisterRequest(username, email, password);

        ApiService service = ApiClient.getClient();
        Call<ResponseBody> call = service.register(request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                String errorMessage = "Registration failed: " + t.getLocalizedMessage();
                Log.e("RegistrationActivity", errorMessage);
                Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

