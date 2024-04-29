package com.example.cookingbysteps.MainActivity;

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

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.RecipeViewActivity;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем контекст для создания интента
                Context context = holder.itemView.getContext();

                // Получаем ID выбранного рецепта
                int recipeId = recipe.getId();

                // Создаем интент для открытия активности RecipeViewActivity и передаем в него ID рецепта
                Intent intent = new Intent(context, RecipeViewActivity.class);
                intent.putExtra("recipe_id", recipeId);

                // Запускаем активность
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView imageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textRecipeTitle);
            descriptionTextView = itemView.findViewById(R.id.textRecipeDescription);
            imageView = itemView.findViewById(R.id.imageRecipe);


        }

        public void bind(Recipe  recipe) {
            titleTextView.setText(recipe.getTitle());
            descriptionTextView.setText(recipe.getDescription());

            byte[] decodedImage = Base64.decode(recipe.getImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            imageView.setImageBitmap(bitmap);
        }
    }
}