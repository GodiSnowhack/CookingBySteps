package com.example.cookingbysteps.RecipeView.Responces;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeStepsResponce {
    @SerializedName("StepsNumber")
    private Integer stepsNumber;

    @SerializedName("Description")
    private String description;

    @SerializedName("Photo")
    private String photo;

    public Integer getStepsNumber(){return stepsNumber;}
    public String getDescription(){return description;}
    public String getPhoto(){return photo;}
}
