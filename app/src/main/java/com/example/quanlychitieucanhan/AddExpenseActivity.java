package com.example.quanlychitieucanhan;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etTitle, etAmount, etNote;
    private Spinner spinnerCategory;
    private DatePicker datePicker;
    private Button btnSave;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        datePicker = findViewById(R.id.datePicker);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        // Setup category spinner
        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String note = etNote.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Tên khoản chi không được để trống");
                etTitle.requestFocus();
                return;
            }

            if (amountStr.isEmpty()) {
                etAmount.setError("Số tiền không được để trống");
                etAmount.requestFocus();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                etAmount.setError("Số tiền không hợp lệ");
                etAmount.requestFocus();
                return;
            }

            if (amount <= 0) {
                etAmount.setError("Số tiền phải lớn hơn 0");
                etAmount.requestFocus();
                return;
            }

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Month is 0-indexed
            int year = datePicker.getYear();
            String date = String.format("%02d/%02d/%d", day, month, year);

            Expense newExpense = new Expense(title, amount, category, date, note);
            ExpenseRepository.getInstance().addExpense(newExpense);

            Toast.makeText(AddExpenseActivity.this, "Thêm khoản chi thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
