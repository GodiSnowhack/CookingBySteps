package com.example.cookingbysteps.RecipeView.RecipeViewFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingbysteps.R;
public class RecipeCommentsFragment extends Fragment {
    Integer id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_comments, container, false);
    }

    public void setId(Integer id) {
        this.id = id;
    }
}