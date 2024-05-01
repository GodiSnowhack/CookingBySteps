package com.example.cookingbysteps.RecipeView.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_row_layout, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.textName.setText(ingredient.getName());
        holder.textQuantity.setText(ingredient.getQuantity());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textQuantity;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.TextName);
            textQuantity = itemView.findViewById(R.id.TextQuantity);
        }
    }
}


