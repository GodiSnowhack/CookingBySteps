package com.example.cookingbysteps.CreateRecipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.R;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_STEP = 0;
    private static final int VIEW_TYPE_HEADER = 1;
    private List<Step> steps;
    private Context context;
    private OnImageClickListener onImageClickListener;

    public StepAdapter(List<Step> steps, Context context) {
        this.steps = steps;
        this.context = context;
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_STEP) {
            View view = inflater.inflate(R.layout.step_item_layout, parent, false);
            return new StepViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.steps_header, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StepViewHolder) {
            Step step = steps.get(position - 1); // Вычитаем 1, чтобы скорректировать позицию
            StepViewHolder stepViewHolder = (StepViewHolder) holder;
            stepViewHolder.bind(step);
        } else if (holder instanceof HeaderViewHolder) {
            // Обработка заголовка
        }
    }

    @Override
    public int getItemCount() {
        // +1 для заголовка
        return steps.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        // Позиция 0 - заголовок, остальные - шаги
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_STEP;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewStepNumber;
        private EditText editTextStepDescription;
        private ImageView imageViewStep;
        private Button deleteButton;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStepNumber = itemView.findViewById(R.id.textViewStepNumber);
            editTextStepDescription = itemView.findViewById(R.id.editTextStepDescription);
            imageViewStep = itemView.findViewById(R.id.imageViewStep);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editTextStepDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition() - 1; // Позиция в списке steps
                    if (position >= 0 && position < steps.size()) {
                        // Обновляем описание шага в списке steps
                        steps.get(position).setDescription(s.toString());
                    }
                }
            });

            imageViewStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(getAdapterPosition() - 1);
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        steps.remove(position - 1);
                        notifyItemRemoved(position);
                    }
                }
            });
        }

        public void bind(Step step) {
            textViewStepNumber.setText("Шаг " + step.getStepNumber()); // Изменено
            editTextStepDescription.setText(step.getDescription());
            String base64Image = step.getImage();
            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageViewStep.setImageBitmap(decodedByte);
            } 
        }


    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewHeader;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);

        }
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public void addStep() {
        int stepNumber = steps.size() + 1; // Увеличиваем на 1 для получения следующего номера шага
        Step step = new Step(stepNumber);
        steps.add(step);
        notifyItemInserted(steps.size());
    }


    public void setImage(int position, String image) {
        if (position < steps.size()) {
            steps.get(position).setImage(image);
            notifyItemChanged(position);
        }
    }

    public class Step {
        private int stepNumber;
        private String description;
        private String image;

        public Step(int stepNumber) {
            this.stepNumber = stepNumber;
        }

        public int getStepNumber() {
            return stepNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
