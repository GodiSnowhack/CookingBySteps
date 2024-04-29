package com.example.cookingbysteps.RecipeView;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class DescriptionRecipeResponce {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("recipeImage")
    private String recipesImage;
    @SerializedName("ingredients")
    private String[] ingredientsName;
    public String getTitle() {return title;}
    public String getDescription(){return description;}
    public String getRecipesImage(){return recipesImage;}
    public String[] getIngredientsName(){return ingredientsName;}
}
