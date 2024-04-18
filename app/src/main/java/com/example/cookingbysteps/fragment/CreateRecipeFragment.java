package com.example.cookingbysteps.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cookingbysteps.CreateRecipePage;
import com.example.cookingbysteps.R;
import com.example.cookingbysteps.StepAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeFragment extends Fragment {

    private Bitmap descBitmap;
    private String recipeTitle, description;
    private String[] ingredients;
    private List<StepAdapter.Step> steps;



    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        Button CreateButton = view.findViewById(R.id.CreateButton);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Проверка 1: Проверить, содержат ли recipeTitle и recipeDescription данные помимо null или ""
                boolean recipeTitle = getRecipeTitle();
                boolean recipeDescription = getRecipeDescription();
                if (recipeTitle && recipeDescription) {
                    // Проверка 2: Проверить, есть ли данные в bitmap во фрагменте DescriptionFragment

                    boolean bitmapAvailable = isBitmapAvailable();
                    if (bitmapAvailable) {
                        // Проверка 3: Проверить, существует ли хотя бы одна строка в таблице во фрагменте IngredientsFragment
                        boolean ingredientsAvailable = isIngredientsAvailable();
                        if (ingredientsAvailable) {
                            // Проверка 4: Проверить, существует ли хотя бы один шаг во фрагменте StepsFragment
                            boolean stepsAvailable = isStepsAvailable();
                            if (stepsAvailable) {
                                // Все проверки пройдены, можно создать рецепт
                                createRecipe();
                            } else {
                                Toast.makeText(getContext(), "Добавьте хотя бы один шаг", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Добавьте хотя бы один ингредиент", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Добавьте изображение рецепта", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Введите название и описание рецепта", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean getRecipeTitle() {
        // Получаем экземпляр DescriptionFragment из ViewPager по индексу
        DescriptionFragment descriptionFragment = (DescriptionFragment) ((CreateRecipePage) getActivity()).getVpAdapter().getItem(0);
        if (descriptionFragment != null) {
            // Получаем текст из DescriptionFragment
            recipeTitle = descriptionFragment.returnRecipeTitle();
            // Проверяем, не пуст ли текст
            return recipeTitle != null && !recipeTitle.isEmpty();
        } else {
            // DescriptionFragment еще не добавлен в ViewPager
            return false;
        }
    }

    private boolean getRecipeDescription() {
        DescriptionFragment descriptionFragment = (DescriptionFragment) ((CreateRecipePage) getActivity()).getVpAdapter().getItem(0);
        if (descriptionFragment != null) {
            // Получаем текст из DescriptionFragment
            description = descriptionFragment.returnRecipeDescription();
            // Проверяем, не пуст ли текст
            return description != null && !description.isEmpty();
        } else {
            // DescriptionFragment еще не добавлен в ViewPager
            return false;
        }
    }

    private boolean isBitmapAvailable() {
        // Получаем экземпляр DescriptionFragment из ViewPager по индексу
        DescriptionFragment descriptionFragment = (DescriptionFragment) ((CreateRecipePage) getActivity()).getVpAdapter().getItem(0);
        if (descriptionFragment != null) {
            // Вызываем метод getBitmap() у экземпляра DescriptionFragment
            descBitmap = descriptionFragment.getBitmap();
            // Проверяем, не является ли bitmap null
            return descBitmap != null;
        } else {
            // DescriptionFragment еще не добавлен в ViewPager
            return false;
        }
    }

    private boolean isIngredientsAvailable() {
        // Получаем экземпляр IngredientsFragment из ViewPager по индексу
        IngredientsFragment ingredientsFragment = (IngredientsFragment) ((CreateRecipePage) getActivity()).getVpAdapter().getItem(1);
        if (ingredientsFragment != null) {
            // Вызываем метод returnIngredients() у экземпляра IngredientsFragment
            ingredients = ingredientsFragment.returnIngredients();
            // Проверяем, есть ли какие-либо ингредиенты
            return ingredients.length > 0;
        } else {
            // IngredientsFragment еще не добавлен в ViewPager
            return false;
        }
    }

    private boolean isStepsAvailable() {
        // Получаем экземпляр StepsFragment из ViewPager по индексу
        StepsFragment stepsFragment = (StepsFragment) ((CreateRecipePage) getActivity()).getVpAdapter().getItem(2);
        if (stepsFragment != null) {
            // Получаем массив шагов из StepsFragment
            steps = stepsFragment.getSteps();
            // Проверяем, не пустой ли массив шагов
            return steps != null && !steps.isEmpty();
        } else {
            // StepsFragment еще не добавлен в ViewPager
            return false;
        }
    }

    public List<Object[]> getStepDataList() {
        List<Object[]> stepDataList = new ArrayList<>();

        for (StepAdapter.Step step : steps) {
            int stepNumber = step.getStepNumber();
            String description = step.getDescription();
            Bitmap image = step.getImage();

            Object[] stepData = {stepNumber, description, image};
            stepDataList.add(stepData);
        }

        return stepDataList;
    }


    @SuppressLint("SetTextI18n")
    private void createRecipe() {
        Toast.makeText(getContext(), "Рецепт создан", Toast.LENGTH_SHORT).show();
    }

}
