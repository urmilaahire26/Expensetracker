import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        loadUserData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Expense Tracker");
            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> register(scanner);
                    case 2 -> login(scanner);
                    case 3 -> {
                        saveUserData();
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("1. Add Expense");
                System.out.println("2. View Expenses");
                System.out.println("3. View Expenses by Category");
                System.out.println("4. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addExpense(scanner);
                    case 2 -> viewExpenses();
                    case 3 -> viewExpensesByCategory();
                    case 4 -> currentUser = null;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("User already exists. Please log in.");
        } else {
            User user = new User(username, password);
            users.put(username, user);
            System.out.println("Registration successful. Please log in.");
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + username + "!");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Expense expense = new Expense(date, category, amount);
        currentUser.addExpense(expense);
        System.out.println("Expense added.");
    }

    private static void viewExpenses() {
        List<Expense> expenses = currentUser.getExpenses();
        System.out.println("Date\t\tCategory\tAmount");
        for (Expense e : expenses) {
            System.out.println(e.getDate() + "\t" + e.getCategory() + "\t\t" + e.getAmount());
        }
    }

    private static void viewExpensesByCategory() {
        Map<String, Double> categoryTotals = currentUser.getCategoryWiseTotal();
        System.out.println("Category\tTotal Amount");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
        }
    }

    private static void loadUserData() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
        @SuppressWarnings("unchecked")
        Map<String, User> loadedUsers = (Map<String, User>) ois.readObject();
        users = loadedUsers;
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("No user data found, starting fresh.");
    }
}

    private static void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving user data.");
        }
    }
}
