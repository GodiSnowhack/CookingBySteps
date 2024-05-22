package com.example.cookingbysteps.RecipeView.RecipeViewFragment;

import android.annotation.SuppressLint;
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
import com.example.cookingbysteps.RecipeView.Adapters.RecipeCommentsAdapter;
import com.example.cookingbysteps.RecipeView.Requests.RecipeGetCommentsRequest;
import com.example.cookingbysteps.RecipeView.Responces.RecipeCommentsResponce;
import com.example.cookingbysteps.RecipeView.Responces.RecipeStepsResponce;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeCommentsFragment extends Fragment {
    Integer recipeID;
    Button sendComments;
    private RecyclerView commentsRecyclerView;
    private RecipeCommentsAdapter commentsAdapter;
    private List<RecipeCommentsResponce> commentsList;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_comments, container, false);

        sendComments = view.findViewById(R.id.callCommentsField);
        sendComments.setOnClickListener(v -> {
            MyDialogFragment dialog = MyDialogFragment.newInstance(recipeID);
            dialog.show(getChildFragmentManager(), "MyDialogFragment");
        });

        // Отправка запроса на сервер для получения комментариев
        commentsRecyclerView = view.findViewById(R.id.stepsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentsList = new ArrayList<>();
        commentsAdapter = new RecipeCommentsAdapter(commentsList);
        commentsRecyclerView.setAdapter(commentsAdapter);

        RecipeGetCommentsRequest request = new RecipeGetCommentsRequest(recipeID);
        ApiService service = ApiClient.getClient();
        Call<List<RecipeCommentsResponce>> call = service.getRecipeComments(request);
        call.enqueue(new Callback<List<RecipeCommentsResponce>>() {
            @Override
            public void onResponse(Call<List<RecipeCommentsResponce>> call, Response<List<RecipeCommentsResponce>> response) {
                if (response.isSuccessful()) {
                    List<RecipeCommentsResponce> recipeCommentsResponces = response.body();
                    if (recipeCommentsResponces != null) {
                        commentsList.clear();
                        commentsList.addAll(recipeCommentsResponces);
                        commentsAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Рецепт загружен", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Ответ сервера пуст", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ошибка ответа сервера", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<RecipeCommentsResponce>> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка загрузки рецепта", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public void setId(Integer id) {
        this.recipeID = id;
    }

}