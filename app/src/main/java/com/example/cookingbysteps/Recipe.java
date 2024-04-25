package com.example.cookingbysteps;

import android.util.Log;

public class Recipe {
    private String title;
    private String description;
    private String recipeImage;

    public Recipe(String title, String description, String recipeImage) {
        this.title = title;
        this.description = description;
        this.recipeImage = recipeImage;
    }

    public String getTitle() {
        Log.d("Название", title);
        return title;
    }

    public String getDescription() {
        Log.d("Описание", description);
        return description;
    }

    public String getImage() {

        return recipeImage;
    }
}


