package com.example.cookingbysteps.MainActivity;

import android.util.Log;

public class Recipe {
    private String title;
    private String description;
    private String recipeImage;
    private Integer id;

    public Recipe(String title, String description, String recipeImage, Integer id) {
        this.title = title;
        this.description = description;
        this.recipeImage = recipeImage;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return recipeImage;
    }

    public Integer getId(){return id;}
}