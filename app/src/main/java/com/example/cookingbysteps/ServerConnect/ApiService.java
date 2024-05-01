package com.example.cookingbysteps.ServerConnect;

import com.example.cookingbysteps.CreateRecipe.RecipeRequest;
import com.example.cookingbysteps.MainActivity.Recipe;
import com.example.cookingbysteps.RecipeView.Requests.CheckRecipeLikeRequest;
import com.example.cookingbysteps.RecipeView.Requests.DescriptionRequest;
import com.example.cookingbysteps.RecipeView.Requests.LikedReceptRequest;
import com.example.cookingbysteps.RecipeView.Requests.RecipeGetCommentsRequest;
import com.example.cookingbysteps.RecipeView.Requests.RecipeInsertCommentsRequest;
import com.example.cookingbysteps.RecipeView.Requests.RecipeStepsRequest;
import com.example.cookingbysteps.RecipeView.Requests.UnlikeReceptRequest;
import com.example.cookingbysteps.RecipeView.Responces.CheckRecipeLikeResponce;
import com.example.cookingbysteps.RecipeView.Responces.DescriptionRecipeResponce;
import com.example.cookingbysteps.RecipeView.Responces.RecipeCommentsResponce;
import com.example.cookingbysteps.RecipeView.Responces.RecipeStepsResponce;
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

    @POST("get_recipe_comments")
    Call<List<RecipeCommentsResponce>> getRecipeComments(@Body RecipeGetCommentsRequest request);

    @POST("insert_recipe_comments")
    Call<ResponseBody> insertRecipeComments(@Body RecipeInsertCommentsRequest request);

    @POST("liked_recept")
    Call<ResponseBody> likedRecept(@Body LikedReceptRequest request);

    @POST("check_liked_recipe")
    Call<CheckRecipeLikeResponce> checkLikedRecipe(@Body CheckRecipeLikeRequest request);

    //@POST("get_liked_recept")
    //Call<GetLikedReceptResponce> getLikedRecept(@Body GetLikedReceptRequest request);

    @POST("/delete_like")
    Call<ResponseBody> unlikeRecept(@Body UnlikeReceptRequest request);
}
