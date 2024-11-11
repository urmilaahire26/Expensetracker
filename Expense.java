import java.io.Serializable;

public class Expense implements Serializable {
    private final String date;
    private final String category;
    private final double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}
