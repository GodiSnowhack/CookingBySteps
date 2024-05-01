package com.example.cookingbysteps.RecipeView.Responces;

import com.google.gson.annotations.SerializedName;

public class RecipeCommentsResponce {
    @SerializedName("AuthorID")
    private Integer AuthorID;

    @SerializedName("Grade")
    private Integer Grade;

    @SerializedName("Comments")
    private String Comments;

    @SerializedName("Username")
    private String Username;

    public String getGrade(){return Grade.toString();}
    public String getComments(){return Comments;}
    public String getUsername(){return Username;}
}
