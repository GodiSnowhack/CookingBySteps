package com.example.cookingbysteps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class CopyPastDeleteActivity extends AppCompatActivity {

    EditText enterText;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_past_delete);

        enterText = findViewById(R.id.EnterText);
        registerForContextMenu(enterText);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        NavigationView myNavView = findViewById(R.id.nav_view_id);
        myNavView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Exit_item) {
                    Toast.makeText(CopyPastDeleteActivity.this, "Выход из приложения",
                            Toast.LENGTH_SHORT).show();
                    finish();

                    return true;
                }
                if (id == R.id.Enter_item) {
                    Toast.makeText(CopyPastDeleteActivity.this, "Переход к входу",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CopyPastDeleteActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.CopyPastDelete_item) {
                    Toast.makeText(CopyPastDeleteActivity.this, "Переход к CopyPastDelete",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CopyPastDeleteActivity.this, CopyPastDeleteActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            int id = item.getItemId();

            if (id == R.id.action_copy) {
                copyText();
                return true;
            }
            if (id == R.id.action_paste) {
                pasteText();
                return true;
            }
            if (id == R.id.action_delete){
                deleteText();
                return true;
            }

            return false;

        };

    private void copyText() {
        String textToCopy = enterText.getText().toString();
        ClipData clipData = ClipData.newPlainText("text", textToCopy);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(CopyPastDeleteActivity.this, "Текст скопирован", Toast.LENGTH_SHORT).show();

        // Установка скопированного текста в TextView
        TextView copyTextView = findViewById(R.id.CopyText);
        copyTextView.setText(textToCopy);
    }

    private void pasteText() {
        if (clipboardManager.hasPrimaryClip()) {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            String pasteText = item.getText().toString();
            enterText.getText().insert(enterText.getSelectionStart(), pasteText);
            Toast.makeText(CopyPastDeleteActivity.this, "Текст вставлен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CopyPastDeleteActivity.this, "Буфер обмена пуст", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteText() {
        enterText.setText("");
        Toast.makeText(CopyPastDeleteActivity.this, "Текст удален", Toast.LENGTH_SHORT).show();
    }
}
