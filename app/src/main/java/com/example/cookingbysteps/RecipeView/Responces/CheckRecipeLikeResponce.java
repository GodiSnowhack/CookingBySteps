package com.example.cookingbysteps.RecipeView.Responces;

import com.google.gson.annotations.SerializedName;

public class CheckRecipeLikeResponce {
    @SerializedName("liked")
    private Integer liked;

    public Integer getLiked(){return liked;}
}
