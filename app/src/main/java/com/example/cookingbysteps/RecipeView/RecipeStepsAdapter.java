package com.example.cookingbysteps.RecipeView;

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

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private List<RecipeStepsResponce> stepsList;

    public RecipeStepsAdapter(List<RecipeStepsResponce> stepsList) {
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recept_steps_layout, parent, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder holder, int position) {
        RecipeStepsResponce currentStep = stepsList.get(position);
        holder.textViewStepNumber.setText("Шаг " + currentStep.getStepsNumber());
        holder.textViewStepDescription.setText(currentStep.getDescription());
        byte[] decodedImage = Base64.decode(currentStep.getPhoto(), Base64.NO_WRAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        holder.imageViewStep.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewStepNumber;
        public TextView textViewStepDescription;
        public ImageView imageViewStep;

        public RecipeStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStepNumber = itemView.findViewById(R.id.textViewStepNumber);
            textViewStepDescription = itemView.findViewById(R.id.TextStepDescription);
            imageViewStep = itemView.findViewById(R.id.imageViewStep);
        }
    }
}
