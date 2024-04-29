package com.example.cookingbysteps.ServerConnect;

import com.example.cookingbysteps.CreateRecipe.RecipeRequest;
import com.example.cookingbysteps.MainActivity.Recipe;
import com.example.cookingbysteps.RecipeView.DescriptionRecipeResponce;
import com.example.cookingbysteps.RecipeView.DescriptionRequest;
import com.example.cookingbysteps.RecipeView.RecipeStepsRequest;
import com.example.cookingbysteps.RecipeView.RecipeStepsResponce;
import com.example.cookingbysteps.RegistrationLogin.LoginRequest;
import com.example.cookingbysteps.RegistrationLogin.LoginResponse;
import com.example.cookingbysteps.RegistrationLogin.RegisterRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register")
    Call<ResponseBody> register(@Body RegisterRequest request);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("create_recipe")
    Call<ResponseBody> createRecipe(@Body RecipeRequest request);

    @GET("get_recipes")
    Call<List<Recipe>> getRecipes();

    @POST("get_recipes_description")
    Call<DescriptionRecipeResponce> getRecipeDescription(@Body DescriptionRequest request);

    @POST("get_recipe_steps")
    Call<List<RecipeStepsResponce>> getRecipeSteps(@Body RecipeStepsRequest request);
}
