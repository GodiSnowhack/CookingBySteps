package com.example.cookingbysteps.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.TableAdapter;

import java.util.ArrayList;
import java.util.List;


public class IngredientsFragment extends Fragment {

    private List<TableAdapter.TableRow> rows;
    private RecyclerView recyclerView;
    private TableAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        // Inflate the layout for this fragment
        rows = new ArrayList<>();

        // Без этой строки все ломается
        rows.add(new TableAdapter.TableRow("Первый продукт", "0"));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TableAdapter(rows);
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Добавляем новую строку после первой строки
                rows.add(1, new TableAdapter.TableRow("Новый продукт", "0"));
                adapter.notifyItemInserted(1);

                // Прокрутка RecyclerView к добавленной строке
                recyclerView.scrollToPosition(1);
            }
        });
        return view;
    }


    // В IngredientsFragment
    public String[] returnIngredients() {
        List<String> ingredientsList = new ArrayList<>();

        // Проходим по всем строкам таблицы
        for (int i = 1; i < rows.size(); i++) {
            // Получаем текущую строку таблицы
            TableAdapter.TableRow row = rows.get(i);

            // Получаем название и количество ингредиента из текущей строки
            String name = row.getName();
            String quantity = row.getQuantity();

            // Формируем строку данных с ингредиентом и добавляем её в список
            String ingredientData = name + " " + quantity;
            ingredientsList.add(ingredientData);
        }

        // Преобразуем список строк в массив строк и возвращаем его
        return ingredientsList.toArray(new String[0]);
    }

}
