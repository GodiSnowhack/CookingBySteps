package com.example.cookingbysteps.RecipeView.Requests;

import com.google.gson.annotations.SerializedName;

public class LikedReceptRequest {
    @SerializedName("recipeID")
    private Integer recipeID;

    @SerializedName("userID")
    private Integer userID;

    public LikedReceptRequest(Integer id, Integer userID){
        this.recipeID = id;
        this.userID = userID;
    }
}
