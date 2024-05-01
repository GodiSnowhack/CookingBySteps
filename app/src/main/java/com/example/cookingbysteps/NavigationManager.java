package com.example.cookingbysteps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cookingbysteps.CreateRecipe.CreateRecipePage;
import com.example.cookingbysteps.LikedRecept.LikedReceptActivity;
import com.example.cookingbysteps.MainActivity.MainActivity;
import com.example.cookingbysteps.RegistrationLogin.RegistrationActivity;
import com.google.android.material.navigation.NavigationView;

public class NavigationManager {
    
    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    @SuppressLint("SetTextI18n")
    public static void setupNavigation(NavigationView navigationView, Activity activity, TextView navUsername) {
        String username = getUsername(activity);
        navUsername.setText(username);


        Menu menu = navigationView.getMenu();
        MenuItem exitAccItem = menu.findItem(R.id.Exit_acc);
        MenuItem createRecipeItem = menu.findItem(R.id.Create_recip);
        MenuItem enterItem = menu.findItem(R.id.Enter_item);
        MenuItem likedRecipes = menu.findItem(R.id.likedRecipes);

        if (username.isEmpty()) {
            exitAccItem.setVisible(false);
            createRecipeItem.setVisible(false);
            likedRecipes.setVisible(false);
            enterItem.setVisible(true);
        } else {
            exitAccItem.setVisible(true);
            createRecipeItem.setVisible(true);
            likedRecipes.setVisible(true);
            enterItem.setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id ==R.id.MainPage){
                    Toast.makeText(activity,"Переход на главную страницу", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
                if (id == R.id.Exit_item) {
                    Toast.makeText(activity, "Выход из приложения", Toast.LENGTH_SHORT).show();
                    activity.finish();
                    return true;
                } else if (id == R.id.Enter_item) {
                    Toast.makeText(activity, "Переход к входу", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, RegistrationActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
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
                    activity.finish();
                } else if (id == R.id.telegram){
                    Toast.makeText(activity, "Переход в телеграмм", Toast.LENGTH_SHORT).show();
                    String telegramBotUrl = "https://t.me/Vkusniy_Marshrut_bot";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegramBotUrl));
                    activity.startActivity(intent);
                    return true;
                } else if (id == R.id.likedRecipes){
                    Toast.makeText(activity, "Понравившиеся рецепты", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, LikedReceptActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

                return false;
            }
        });
    }


}