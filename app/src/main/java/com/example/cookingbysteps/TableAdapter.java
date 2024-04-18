package com.example.cookingbysteps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ROW = 1;
    private List<TableRow> rows;
    public TableAdapter(List<TableRow> rows) {
        this.rows = rows;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ROW;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.header_table_layout, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            return new RowViewHolder(rowView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RowViewHolder) {
            TableRow row = rows.get(position);
            RowViewHolder rowViewHolder = (RowViewHolder) holder;

            // Получаем данные из TableRow объекта и устанавливаем их в EditText
            rowViewHolder.editTextName.setText(row.getName());
            rowViewHolder.editTextQuantity.setText(row.getQuantity());

            // Устанавливаем обработчики изменений текста в EditText для обновления данных в TableRow
            rowViewHolder.editTextName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Не требуется
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Обновляем имя в TableRow при изменении текста в EditText
                    row.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Не требуется
                }
            });

            rowViewHolder.editTextQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Не требуется
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Обновляем количество в TableRow при изменении текста в EditText
                    row.setQuantity(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Не требуется
                }
            });

            // Устанавливаем обработчик нажатия на кнопку удаления
            rowViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        removeRow(adapterPosition);
                    }
                }
            });
        }
    }





    @Override
    public int getItemCount() {
        return rows.size();
    }

    // ViewHolder для заголовка столбцов
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewColumnName;
        TextView textViewColumnQuantity;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewColumnName = itemView.findViewById(R.id.textViewColumnName);
            textViewColumnQuantity = itemView.findViewById(R.id.textViewColumnQuantity);
        }
    }

    // ViewHolder для строки таблицы
    private static class RowViewHolder extends RecyclerView.ViewHolder {

        Button deleteButton;
        EditText editTextName;
        EditText editTextQuantity;

        RowViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextQuantity = itemView.findViewById(R.id.editTextQuantity);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public static class TableRow {
        private String name;
        private String quantity;

        public TableRow(String name, String quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public String setName(String string){
            this.name = string;
            return string;
        }

        public String getQuantity() {
            return quantity;
        }

        public String setQuantity(String string){
            this.quantity = string;
            return string;
        }

    }

    public void removeRow(int position) {
        rows.remove(position);
        notifyItemRemoved(position);
    }
}


