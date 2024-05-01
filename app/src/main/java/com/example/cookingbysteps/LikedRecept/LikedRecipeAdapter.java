package com.example.cookingbysteps.LikedRecept;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.MainActivity.Recipe;
import com.example.cookingbysteps.MainActivity.RecipeAdapter;
import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.RecipeViewActivity;

import java.util.List;

public class LikedRecipeAdapter extends RecyclerView.Adapter<LikedRecipeAdapter.LikedRecipeViewHolder> {
    private List<LikedRecipe> recipes;

    public LikedRecipeAdapter(List<LikedRecipe> recipes){this.recipes = recipes;}

    @NonNull
    @Override
    public LikedRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout, parent, false);
        return new LikedRecipeAdapter.LikedRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedRecipeAdapter.LikedRecipeViewHolder holder, int position) {
        LikedRecipe recipe = recipes.get(position);
        holder.bind(recipe);

        holder.itemView.setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            int recipeId = recipe.getId();
            Intent intent = new Intent(context, RecipeViewActivity.class);
            intent.putExtra("recipe_id", recipeId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class LikedRecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView imageView;

        public LikedRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textRecipeTitle);
            descriptionTextView = itemView.findViewById(R.id.textRecipeDescription);
            imageView = itemView.findViewById(R.id.imageRecipe);
        }

        public void bind(LikedRecipe  recipe) {
            titleTextView.setText(recipe.getTitle());
            descriptionTextView.setText(recipe.getDescription());

            byte[] decodedImage = Base64.decode(recipe.getImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            imageView.setImageBitmap(bitmap);
        }
    }
}
