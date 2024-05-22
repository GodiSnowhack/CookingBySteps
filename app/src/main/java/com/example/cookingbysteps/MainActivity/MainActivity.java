package com.example.cookingbysteps.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.NavigationManager;
import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RegistrationLogin.RegistrationActivity;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavigationView myNavView = findViewById(R.id.nav_view_id);
        View headerView = myNavView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.UsernameText);

        NavigationManager.setupNavigation(myNavView, this, navUsername);

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userID", -1);

        if (userId > 0) {
            showLoginDialog();
        }

        // Получение токена устройства для регистрации в Firebase Cloud Messaging
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Получение токена
                    String token = task.getResult();
                    Log.d("FCM", "FCM token: " + token);
                });

        fetchRecipes();
    }

    private void fetchRecipes() {
        ApiService service = ApiClient.getClient();
        Call<List<Recipe>> call = service.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> recipeList = response.body();
                    if (recipeList != null) {
                        adapter = new RecipeAdapter(recipeList);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch recipes: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Нужна авторизация");
        builder.setMessage("Вам необходимо войти в систему, чтобы получить доступ.");
        builder.setPositiveButton("Авторизоваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open RegisLoginActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }
        });

        builder.create().show();
    }

    private void showTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добро пожаловать");
        builder.setMessage("Вы находитесь на главной странице приложения. Тут можно будет посмотреть весь список рецептов." +
                " Для навигации по приложении прошу потянуть слева направо навигационную панель.");

        builder.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTipDialog();
    }
}