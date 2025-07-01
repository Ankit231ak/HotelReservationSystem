import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class x {
    static Connection con = null;

    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/temp2";
        String username = "root";
        String password = "90901212";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully ✔️");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);

        double balance = getLastBalance(); 
        

        boolean isRunning = true;
    

        while (isRunning) {
            System.out.println("\nSelect Option:");
            System.out.println("1. Show Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Exit");
            System.out.print("Select (1, 2, 3, 4): ");

            byte choice;
            try {
                choice = sc.nextByte();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1 -> balance = showBalance(balance);
                case 2 -> balance = deposit(balance, sc);
                case 3 -> balance = withdrawal(balance, sc);
                case 4 -> isRunning = false;
                default -> System.out.println("Invalid choice. Please select 1-4.");
            }
        }

        System.out.println("Thank you for using our ATM!");
        sc.close();

        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error closing DB connection: " + e.getMessage());
        }
    }

    static double showBalance(double balance) {
        if (balance > 0) {
            balance -= 1;
            System.out.println("1rs charged for balance inquiry.");
        }
        System.out.println("Balance: " + balance);
        return balance;
    }

    static double deposit(double balance, Scanner sc) {
        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();
        if (amount > 0) {
            balance += amount;
            saveBalanceToDatabase(balance);
        } else {
            System.out.println("Invalid amount.");
        }
        System.out.println("Balance: " + balance);
        return balance;
    }

    static double withdrawal(double balance, Scanner sc) {
        System.out.print("Enter withdrawal amount: ");
        double amount = sc.nextDouble();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            saveBalanceToDatabase(balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
        System.out.println("Balance: " + balance);
        return balance;
    }

    static void saveBalanceToDatabase(double balance) {
        try {
            String query = "INSERT INTO salary(salary) VALUES (" + balance + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("✅ Balance saved to database.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to save balance: " + e.getMessage());
        }

    }
    static double getLastBalance() {
        double lastBalance = 0;
        try {
            Statement stmt = con.createStatement();
            var  rs = stmt.executeQuery("SELECT salary FROM salary ORDER BY id DESC LIMIT 1");
            if (rs.next()) {
                lastBalance = rs.getDouble("salary");
            }
        } catch (SQLException e) {
            System.out.println("❌ Could not fetch last balance: " + e.getMessage());
        }
        return lastBalance;
    }

}
