package com.example.cookingbysteps.RecipeView.Requests;

import com.google.gson.annotations.SerializedName;

public class CheckRecipeLikeRequest {
    @SerializedName("UserID")
    private Integer userID;

    @SerializedName("receptID")
    private Integer receptID;


    public CheckRecipeLikeRequest(Integer receptID, Integer id){
        this.receptID = receptID;
        this.userID = id;
    }
}
