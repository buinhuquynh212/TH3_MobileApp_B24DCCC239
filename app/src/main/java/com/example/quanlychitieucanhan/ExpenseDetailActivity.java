package com.example.quanlychitieucanhan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAmount, tvCategory, tvDate, tvNote;
    private Button btnBackDetail, btnDeleteExpense;
    private ImageView btnBackBar;
    private Expense currentExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAmount = findViewById(R.id.tvDetailAmount);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvDate = findViewById(R.id.tvDetailDate);
        tvNote = findViewById(R.id.tvDetailNote);
        btnBackDetail = findViewById(R.id.btnBackDetail);
        btnDeleteExpense = findViewById(R.id.btnDeleteExpense);
        btnBackBar = findViewById(R.id.btnBackBar);

        int expenseId = getIntent().getIntExtra("expense_id", -1);
        List<Expense> expenses = ExpenseRepository.getInstance().getExpenseList();
        for (Expense e : expenses) {
            if (e.getId() == expenseId) {
                currentExpense = e;
                break;
            }
        }

        if (currentExpense == null) {
            Toast.makeText(this, "Không tìm thấy khoản chi", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        DecimalFormat formatter = new DecimalFormat("#,###");

        tvTitle.setText(currentExpense.getTitle());
        tvAmount.setText(formatter.format(currentExpense.getAmount()) + " đ");
        tvCategory.setText(currentExpense.getCategory());
        tvDate.setText(currentExpense.getDate());
        tvNote.setText(currentExpense.getNote().isEmpty() ? "(Không có ghi chú)" : currentExpense.getNote());

        btnBackBar.setOnClickListener(v -> finish());
        btnBackDetail.setOnClickListener(v -> finish());

        btnDeleteExpense.setOnClickListener(v -> {
            ExpenseRepository.getInstance().removeExpense(currentExpense);
            Toast.makeText(ExpenseDetailActivity.this, "Đã xóa khoản chi", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
