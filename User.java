import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final List<Expense> expenses;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public Map<String, Double> getCategoryWiseTotal() {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.put(expense.getCategory(),
                    categoryTotals.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }
        return categoryTotals;
    }
}
