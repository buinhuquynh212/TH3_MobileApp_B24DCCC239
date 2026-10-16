package com.example.quanlychitieucanhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private LinearLayout llExpenseList;
    private Button btnAddExpense;
    private ExpenseRepository repository;
    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        llExpenseList = findViewById(R.id.llExpenseList);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        repository = ExpenseRepository.getInstance();
        formatter = new DecimalFormat("#,###");

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        loadExpenses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }

    private void loadExpenses() {
        llExpenseList.removeAllViews();
        List<Expense> expenses = repository.getExpenseList();
        double total = 0;

        for (int i = 0; i < expenses.size(); i++) {
            final Expense expense = expenses.get(i);
            total += expense.getAmount();

            View rowView = LayoutInflater.from(this).inflate(R.layout.row_expense, llExpenseList, false);

            TextView tvTitle = rowView.findViewById(R.id.tvExpenseTitle);
            TextView tvAmount = rowView.findViewById(R.id.tvExpenseAmount);
            TextView tvCategory = rowView.findViewById(R.id.tvExpenseCategory);
            TextView tvDate = rowView.findViewById(R.id.tvExpenseDate);

            tvTitle.setText(expense.getTitle());
            tvAmount.setText(formatter.format(expense.getAmount()) + " đ");
            tvCategory.setText(expense.getCategory());
            tvDate.setText(expense.getDate());

            rowView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("expense_id", expense.getId());
                startActivity(intent);
            });

            llExpenseList.addView(rowView);
        }

        tvTotalAmount.setText(formatter.format(total) + " đ");
    }
}
