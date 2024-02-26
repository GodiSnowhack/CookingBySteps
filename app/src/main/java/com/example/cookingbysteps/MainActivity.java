package com.example.cookingbysteps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView myNavView = findViewById(R.id.nav_view_id);
        myNavView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Exit_item) {
                    Toast.makeText(MainActivity.this, "Выход из приложения",
                            Toast.LENGTH_SHORT).show();
                    finish();

                    return true;
                } else if (id == R.id.Enter_item) {
                    Toast.makeText(MainActivity.this, "Переход к входу",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
    }
}