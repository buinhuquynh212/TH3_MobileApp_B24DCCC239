package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private Context context;
    private List<Expense> expenses;
    private DecimalFormat formatter;

    public ExpenseAdapter(@NonNull Context context, @NonNull List<Expense> expenses) {
        super(context, R.layout.row_expense, expenses);
        this.context = context;
        this.expenses = expenses;
        this.formatter = new DecimalFormat("#,###");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_expense, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvExpenseTitle);
        TextView tvAmount = convertView.findViewById(R.id.tvExpenseAmount);
        TextView tvCategory = convertView.findViewById(R.id.tvExpenseCategory);
        TextView tvDate = convertView.findViewById(R.id.tvExpenseDate);

        tvTitle.setText(expense.getTitle());
        tvAmount.setText(formatter.format(expense.getAmount()) + " đ");
        tvCategory.setText(expense.getCategory());
        tvDate.setText(expense.getDate());

        return convertView;
    }
}
