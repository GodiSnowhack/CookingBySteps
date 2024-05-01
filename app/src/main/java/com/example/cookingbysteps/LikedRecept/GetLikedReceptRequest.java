package com.example.cookingbysteps.LikedRecept;

import com.google.gson.annotations.SerializedName;

public class GetLikedReceptRequest {
    @SerializedName("UserID")
    private Integer userID;
    public GetLikedReceptRequest(Integer id){
        this.userID = id;
    }

}
