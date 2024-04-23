package com.example.cookingbysteps.CreateRecipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeRequest {
    @SerializedName("AuthorID")
    private String authorId;

    @SerializedName("Title")
    private String title;

    @SerializedName("Description")
    private String description;

    @SerializedName("RecipeImage")
    private String recipeImage; // Изображение в виде строки (URL или base64)

    @SerializedName("Ingredients")
    private String[] ingredients;

    @SerializedName("Steps")
    private List<Object[]> steps; // Массив данных для шагов

    public RecipeRequest(String authorId, String title, String description, String recipeImage, String[] ingredients, List<Object[]> steps){
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
        this.steps = steps;
    }

}

