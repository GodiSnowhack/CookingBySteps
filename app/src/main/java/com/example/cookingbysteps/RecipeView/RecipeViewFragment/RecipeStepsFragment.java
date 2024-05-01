package com.example.cookingbysteps.RecipeView.RecipeViewFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.Adapters.RecipeStepsAdapter;
import com.example.cookingbysteps.RecipeView.Requests.LikedReceptRequest;
import com.example.cookingbysteps.RecipeView.Requests.RecipeStepsRequest;
import com.example.cookingbysteps.RecipeView.Responces.RecipeStepsResponce;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeStepsFragment extends Fragment {

    private RecyclerView stepsRecyclerView;
    private RecipeStepsAdapter stepsAdapter;
    private List<RecipeStepsResponce> stepsList;

    Integer id;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        stepsRecyclerView = view.findViewById(R.id.stepsRecyclerView);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsList = new ArrayList<>();
        stepsAdapter = new RecipeStepsAdapter(stepsList);
        stepsRecyclerView.setAdapter(stepsAdapter);

        RecipeStepsRequest request = new RecipeStepsRequest(id);

        ApiService service = ApiClient.getClient();
        Call<List<RecipeStepsResponce>> call = service.getRecipeSteps(request);

        call.enqueue(new Callback<List<RecipeStepsResponce>>() {
            @Override
            public void onResponse(Call<List<RecipeStepsResponce>> call, Response<List<RecipeStepsResponce>> response) {
                if (response.isSuccessful()) {
                    List<RecipeStepsResponce> recipeStepsResponce = response.body();
                    if (recipeStepsResponce != null) {
                        stepsList.clear();
                        stepsList.addAll(recipeStepsResponce);
                        stepsAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Рецепт загружен", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Ответ сервера пуст", Toast.LENGTH_SHORT).show();
                    }
                } else {
                     Toast.makeText(getContext(), "Ошибка ответа сервера", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<RecipeStepsResponce>> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка загрузки рецепта", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
