package com.example.quanlychitieucanhan;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private static ExpenseRepository instance;
    private List<Expense> expenseList;

    private ExpenseRepository() {
        expenseList = new ArrayList<>();
        // Sample data matching requirements / design
        expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", "12/10/2026", "Ăn sáng phở bò"));
        expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", "12/10/2026", "Đổ xăng xe máy"));
        expenseList.add(new Expense("Mua sách", 120000, "Học tập", "13/10/2026", "Sách Lập trình Android"));
    }

    public static synchronized ExpenseRepository getInstance() {
        if (instance == null) {
            instance = new ExpenseRepository();
        }
        return instance;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public void removeExpense(Expense expense) {
        expenseList.remove(expense);
    }

    public void removeExpenseById(int id) {
        expenseList.removeIf(e -> e.getId() == id);
    }

    public double getTotalAmount() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        return total;
    }
}
