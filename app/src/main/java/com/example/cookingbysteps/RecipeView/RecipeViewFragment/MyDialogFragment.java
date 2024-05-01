package com.example.cookingbysteps.RecipeView.RecipeViewFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.Requests.RecipeInsertCommentsRequest;
import com.example.cookingbysteps.ServerConnect.ApiClient;
import com.example.cookingbysteps.ServerConnect.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDialogFragment extends DialogFragment {
    private EditText commentEditText;
    private RatingBar ratingBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        Integer receptID = getArguments().getInt("receptID");

        commentEditText = view.findViewById(R.id.commentEditText);
        ratingBar = view.findViewById(R.id.ratingBar);

        builder.setView(view)
                .setTitle("Оставить комментарий и оценку")
                .setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        Integer authorId = sharedPreferences.getInt("userID", -1);
                        String comment = commentEditText.getText().toString();
                        float floatRating = ratingBar.getRating();
                        int intRating = (int) floatRating;
                        Integer ratingInteger = Integer.valueOf(intRating);

                        RecipeInsertCommentsRequest request = new RecipeInsertCommentsRequest(receptID,authorId,ratingInteger,comment);

                        ApiService service = ApiClient.getClient();
                        Call<ResponseBody> call = service.insertRecipeComments(request);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getContext(), "Комментарий написан", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(), "Создать комментарий не получилось: " + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "Создать комментарий не получилось: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        // Здесь вы можете использовать комментарий и оценку
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public static MyDialogFragment newInstance(Integer receptID) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putInt("receptID", receptID);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }
}


