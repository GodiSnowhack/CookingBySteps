package com.example.cookingbysteps.LikedRecept;

public class LikedRecipe {
    private String Title;
    private String Description;
    private String RecipesImage;
    private Integer id;

    public LikedRecipe(String title, String description, String recipeImage, Integer id) {
        this.Title = title;
        this.Description = description;
        this.RecipesImage = recipeImage;
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return RecipesImage;
    }

    public Integer getId(){return id;}
}
