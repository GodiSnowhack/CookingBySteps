package com.example.cookingbysteps.RecipeView.Requests;

import com.google.gson.annotations.SerializedName;

public class RecipeInsertCommentsRequest {
    @SerializedName("recipeID")
    private Integer recipeID;

    @SerializedName("AuthorID")
    private Integer authorID;

    @SerializedName("Grade")
    private Integer grade;

    @SerializedName("Comments")
    private String comments;

    public RecipeInsertCommentsRequest(Integer id, Integer authorID, Integer grade, String comments){
        this.recipeID = id;
        this.authorID = authorID;
        this.grade = grade;
        this.comments = comments;
    }
}
