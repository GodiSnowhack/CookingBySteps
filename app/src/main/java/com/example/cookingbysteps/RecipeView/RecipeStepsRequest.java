package com.example.cookingbysteps.RecipeView;

import com.google.gson.annotations.SerializedName;

public class RecipeStepsRequest {
    @SerializedName("recipeID")
    private Integer recipeID;

    public RecipeStepsRequest(Integer id){
        this.recipeID = id;
    }
}
