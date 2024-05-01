package com.example.cookingbysteps.RecipeView.Requests;

import com.google.gson.annotations.SerializedName;

public class UnlikeReceptRequest {
    @SerializedName("UserID")
    private Integer userID;

    @SerializedName("receptID")
    private Integer receptID;


    public UnlikeReceptRequest(Integer id, Integer usertID){
        this.receptID = id;
        this.userID = usertID;
    }
}
