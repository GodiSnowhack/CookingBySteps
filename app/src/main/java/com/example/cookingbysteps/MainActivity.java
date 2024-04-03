package com.example.cookingbysteps;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView myNavView = findViewById(R.id.nav_view_id);
        View headerView = myNavView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.UsernameText);

        NavigationManager.setupNavigation(myNavView, this, navUsername);
    }

}