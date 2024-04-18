package com.example.cookingbysteps.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.StepAdapter;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StepsFragment extends Fragment {

    private List<StepAdapter.Step> steps;
    private StepAdapter adapter;
    private int currentStepPosition = -1;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        steps = new ArrayList<>();
        adapter = new StepAdapter(steps, getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewSteps);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.addStepButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addStep();
            }
        });

        adapter.setOnImageClickListener(new StepAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                currentStepPosition = position;
                openImageChooser();
            }
        });

        return view;
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        adapter.setOnImageClickListener((StepAdapter.OnImageClickListener) listener);
    }

    public List<StepAdapter.Step> getSteps() {
        return steps;
    }

    public void onImageClick(int position) {
        // Обработка нажатия на изображение для элемента списка с позицией position
        // Здесь вы можете выполнить дополнительные действия, связанные с этой позицией
        // Например, показать диалог выбора изображения для соответствующего шага
        openImageChooser();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = getBitmapFromUri(uri);
                if (currentStepPosition != -1) {
                    adapter.setImage(currentStepPosition, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = requireContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
