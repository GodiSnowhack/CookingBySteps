package com.example.cookingbysteps.RecipeView.Requests;

import com.google.gson.annotations.SerializedName;

public class RecipeGetCommentsRequest {
    @SerializedName("recipeID")
    private Integer recipeID;

    public RecipeGetCommentsRequest(Integer id){
        this.recipeID = id;
    }
}
