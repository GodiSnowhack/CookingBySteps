package com.example.cookingbysteps;

import android.annotation.SuppressLint;
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

import com.google.android.material.navigation.NavigationView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText UsernameEditRegis, EmailEditRegis, PasswordEditRegis, EmailEditLogin, PasswordEditLogin;
    private Button RegisButton, LoginButton, ChangeMode;
    View RegisLayout, LoginLayout;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String email = sharedPreferences.getString("email", null);



        NavigationView myNavView = findViewById(R.id.nav_view_id);

        if (username != null && email != null) {
            // Пользователь авторизован
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show();

            // Обновляем текст TextView в навигации
            View headerView = myNavView.getHeaderView(0);
            TextView navUsername = headerView.findViewById(R.id.UsernameText);
            navUsername.setText(username);
        } else {
            // Пользователь не авторизован
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }

        NavigationManager.setupNavigation(myNavView, this);

        RegisLayout = findViewById(R.id.RegisLayout);
        LoginLayout = findViewById(R.id.LoginLayout);

        ChangeMode = findViewById(R.id.ChangeMode);
        ChangeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RegisLayout.getVisibility() == View.VISIBLE) {
                    RegisLayout.setVisibility(View.GONE);
                    LoginLayout.setVisibility(View.VISIBLE);
                } else { // Иначе, если LoginLayout видим, то скрываем его и показываем RegisLayout
                    RegisLayout.setVisibility(View.VISIBLE);
                    LoginLayout.setVisibility(View.GONE);
                }
            }
        });

        UsernameEditRegis = findViewById(R.id.UsernameEditRegis);
        EmailEditRegis = findViewById(R.id.EmailEditRegis);
        PasswordEditRegis = findViewById(R.id.PasswordEditRegis);
        RegisButton = findViewById(R.id.RegisButton);

        RegisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        EmailEditLogin = findViewById(R.id.EmailEditLogin);
        PasswordEditLogin = findViewById(R.id.PasswordEditLogin);
        LoginButton = findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = EmailEditLogin.getText().toString();
        String password = PasswordEditLogin.getText().toString();

        LoginRequest request = new LoginRequest(email, password);

        ApiService service = ApiClient.getClient();
        Call<LoginResponse> call = service.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String username = loginResponse.getUsername();
                    String email = loginResponse.getEmail();

                    SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("email", email);
                    editor.apply();
                    Toast.makeText(RegistrationActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
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


    private void register() {
        String username = UsernameEditRegis.getText().toString();
        String email = EmailEditRegis.getText().toString();
        String password = PasswordEditRegis.getText().toString();

        RegisterRequest request = new RegisterRequest(username, email, password);

        ApiService service = ApiClient.getClient();
        Call<ResponseBody> call = service.register(request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = "Registration failed: " + t.getLocalizedMessage();
                Log.e("RegistrationActivity", errorMessage);
                Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

