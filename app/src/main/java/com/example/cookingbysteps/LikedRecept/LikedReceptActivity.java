package com.example.cookingbysteps.LikedRecept;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingbysteps.MainActivity.MainActivity;
import com.example.cookingbysteps.MainActivity.Recipe;
import com.example.cookingbysteps.MainActivity.RecipeAdapter;
import com.example.cookingbysteps.NavigationManager;
import com.example.cookingbysteps.R;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedReceptActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LikedRecipeAdapter adapter;
    private Integer userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_recept);

        NavigationView myNavView = findViewById(R.id.nav_view_id);
        View headerView = myNavView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.UsernameText);
        NavigationManager.setupNavigation(myNavView, this, navUsername);

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchRecipes();
    }

    private void fetchRecipes() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userID", -1);
        ApiService service = ApiClient.getClient();
        GetLikedReceptRequest request = new GetLikedReceptRequest(userId);

        Call <List<LikedRecipe>> call = service.getLikedRecept(request);
        call.enqueue(new Callback<List<LikedRecipe>>() {
            @Override
            public void onResponse(Call<List<LikedRecipe>> call, Response<List<LikedRecipe>> response) {
                if (response.isSuccessful()) {
                    List<LikedRecipe> recipeList = response.body();
                    if (recipeList != null) {
                        adapter = new LikedRecipeAdapter(recipeList);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(LikedReceptActivity.this, "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<LikedRecipe>> call, Throwable t) {
                Toast.makeText(LikedReceptActivity.this, "Failed to fetch recipes: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}