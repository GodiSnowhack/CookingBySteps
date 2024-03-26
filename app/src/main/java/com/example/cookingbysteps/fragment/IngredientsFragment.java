package com.example.cookingbysteps.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        // Добавляем первую строку
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
}
