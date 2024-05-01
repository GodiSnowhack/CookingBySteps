package com.example.cookingbysteps.RecipeView.Requests;

import com.google.gson.annotations.SerializedName;

public class DescriptionRequest {
    @SerializedName("recipeID")
    private Integer recipeID;

    public DescriptionRequest(Integer id){
        this.recipeID = id;
    }
}
