package com.example.cookingbysteps.RecipeView.RecipeViewFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.Requests.CheckRecipeLikeRequest;
import com.example.cookingbysteps.RecipeView.Requests.LikedReceptRequest;
import com.example.cookingbysteps.RecipeView.Requests.UnlikeReceptRequest;
import com.example.cookingbysteps.RecipeView.Responces.CheckRecipeLikeResponce;
import com.example.cookingbysteps.RecipeView.Responces.DescriptionRecipeResponce;
import com.example.cookingbysteps.RecipeView.Requests.DescriptionRequest;
import com.example.cookingbysteps.RecipeView.Ingredient;
import com.example.cookingbysteps.RecipeView.Adapters.IngredientAdapter;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDescriptionFragment extends Fragment {

    Integer id , userId;
    TextView recipeTitle, recipeDescription;
    ImageView imageView;
    ApiService service;

    private Button likeButton, unlikeButton;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);

        recipeTitle = view.findViewById(R.id.RecipeTitle);
        recipeDescription = view.findViewById(R.id.RecipeDescription);
        imageView = view.findViewById(R.id.ImageView);
        likeButton = view.findViewById(R.id.LikeButton);
        unlikeButton = view.findViewById(R.id.UnlikeButtom);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userID", -1);

        service = ApiClient.getClient();
        CheckRecipeLikeRequest request1 = new CheckRecipeLikeRequest(id, userId);
        Call<CheckRecipeLikeResponce> call1 = service.checkLikedRecipe(request1);
        call1.enqueue(new Callback<CheckRecipeLikeResponce>() {
            @Override
            public void onResponse(Call<CheckRecipeLikeResponce> call, Response<CheckRecipeLikeResponce> response) {
                if (response.isSuccessful()){
                    CheckRecipeLikeResponce checkRecipeLikeResponce = response.body();

                    Integer liked = checkRecipeLikeResponce.getLiked();
                    if (liked == 1){
                        likeButton.setVisibility(View.INVISIBLE);
                        unlikeButton.setVisibility(View.VISIBLE);
                    }
                    if (liked == 0)
                    {
                        likeButton.setVisibility(View.VISIBLE);
                        unlikeButton.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckRecipeLikeResponce> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });

        unlikeButton.setOnClickListener(v -> {
            UnlikeReceptRequest request = new UnlikeReceptRequest(id, userId);
            Call<ResponseBody> call = service.unlikeRecept(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "Лайк убран", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            });
        });


        likeButton.setOnClickListener(v -> {
            LikedReceptRequest request = new LikedReceptRequest(id, userId);
            Call<ResponseBody> call = service.likedRecept(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Лайк поставлен", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            });
        });

        DescriptionRequest request = new DescriptionRequest(id);

        Call<DescriptionRecipeResponce> call = service.getRecipeDescription(request);

        call.enqueue(new Callback<DescriptionRecipeResponce>() {
            @Override
            public void onResponse(Call<DescriptionRecipeResponce> call, Response<DescriptionRecipeResponce> response) {
                if (response.isSuccessful()) {
                    DescriptionRecipeResponce descriptionRecipeResponce = response.body();
                    if (descriptionRecipeResponce != null) {
                        putData(view, descriptionRecipeResponce.getTitle(), descriptionRecipeResponce.getDescription(), descriptionRecipeResponce.getRecipesImage(), descriptionRecipeResponce.getIngredientsName());
                    } else {
                        Toast.makeText(getContext(), "Ответ сервера пуст", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ошибка ответа сервера", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DescriptionRecipeResponce> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка загрузки рецепта", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void putData(View view, String title, String description, String recipesImage, String[] ingredientsName) {
        recipeTitle.setText(title);
        recipeDescription.setText(description);

        byte[] decodedImage = Base64.decode(recipesImage, Base64.NO_WRAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        imageView.setImageBitmap(bitmap);

        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredient : ingredientsName) {
            String[] parts = ingredient.split("!");
            String name = parts[0];
            String quantity = parts[1];
            ingredients.add(new Ingredient(name, quantity));
        }

        IngredientAdapter adapter = new IngredientAdapter(ingredients);
        RecyclerView recyclerView = view.findViewById(R.id.DescIngredientsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    public void setId(Integer id) {
        this.id = id;
    }

}