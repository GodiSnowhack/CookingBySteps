package com.example.cookingbysteps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cookingbysteps.RegistrationLogin.RegistrationActivity;
import com.google.android.material.navigation.NavigationView;

public class NavigationManager {
    private static SharedPreferences sharedPreferences;

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    public static void setupNavigation(NavigationView navigationView, Activity activity, TextView navUsername) {

        String username = getUsername(activity);
        navUsername.setText(username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Exit_item) {
                    Toast.makeText(activity, "Выход из приложения", Toast.LENGTH_SHORT).show();
                    activity.finish();
                    return true;
                } else if (id == R.id.Enter_item) {
                    Toast.makeText(activity, "Переход к входу", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, RegistrationActivity.class);
                    activity.startActivity(intent);
                    return true;
                } else if (id == R.id.Exit_acc) {
                    SharedPreferences sharedPreferences = activity.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(activity, "Выход из аккаунта", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.Create_recip){
                    Toast.makeText(activity, "Создать рецепт", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, CreateRecipePage.class);
                    activity.startActivity(intent);
                }


                return false;
            }
        });
    }
}