package com.example.cookingbysteps.RecipeView.Adapters;

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
import com.example.cookingbysteps.RecipeView.Responces.RecipeCommentsResponce;

import java.util.List;

public class RecipeCommentsAdapter extends RecyclerView.Adapter<RecipeCommentsAdapter.RecipeCommentsViewHolder> {
    private List<RecipeCommentsResponce> commentsList;

    public RecipeCommentsAdapter(List<RecipeCommentsResponce> stepsList) {
        this.commentsList = stepsList;
    }

    @NonNull
    @Override
    public RecipeCommentsAdapter.RecipeCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_comments_item, parent, false);
        return new RecipeCommentsAdapter.RecipeCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCommentsAdapter.RecipeCommentsViewHolder holder, int position) {
        RecipeCommentsResponce currentStep = commentsList.get(position);
        holder.textCommentsAuthor.setText(currentStep.getUsername());
        holder.textCommentsText.setText(currentStep.getComments());
        holder.textCommentsGrade.setText(currentStep.getGrade());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class RecipeCommentsViewHolder extends RecyclerView.ViewHolder {
        public TextView textCommentsAuthor, textCommentsText, textCommentsGrade;

        public RecipeCommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            textCommentsAuthor = itemView.findViewById(R.id.textCommentsAuthor);
            textCommentsText = itemView.findViewById(R.id.textCommentsText);
            textCommentsGrade = itemView.findViewById(R.id.textCommentsGrade);
        }
    }
}
