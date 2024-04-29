package com.example.cookingbysteps.CreateRecipe.CreateFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.cookingbysteps.R;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;


public class DescriptionFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    private EditText editRecipeTitle, editDescription;
    private Button LockButton;
    private SharedPreferences sharedPreferences;
    private String base64;
    private String title = "";
    private String description = "";


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        imageView = view.findViewById(R.id.ReceptImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        editRecipeTitle = view.findViewById(R.id.editRecipeTitle);
        editDescription = view.findViewById(R.id.editDescription);
        return view;
    }

    // Метод для открытия выбора изображения
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST);
    }

    // Метод для обработки результатов выбора изображения
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                base64 = getBase64FromUri(uri);
                // Установка base64 в ImageView
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(base64, Base64.DEFAULT), 0, Base64.decode(base64, Base64.DEFAULT).length));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для конвертации изображения в base64
    public String getBase64FromUri(Uri uri) throws IOException {
        Bitmap bitmap = getBitmapFromUri(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    // Метод для получения Bitmap из Uri
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = requireContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    // Публичный метод для получения текста из editRecipeTitle
    public String returnRecipeTitle() {
        return title = editRecipeTitle.getText().toString();
    }

    // Публичный метод для получения текста из editDescription
    public String returnRecipeDescription() {
        return description = editDescription.getText().toString();
    }

    public String returnRecipeImage(){
        return base64;
    }
}
