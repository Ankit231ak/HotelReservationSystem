import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class f {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "90901212";
        String query = "SELECT * FROM ankit WHERE id = ?;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            return;
        }

        try (
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            System.out.print("Enter id for info: ");
            int ids = sc.nextInt();
            pstmt.setInt(1, ids);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int salary = rs.getInt("salary");

                System.out.println("\nEmployee Details:");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Salary: " + salary);
            } else {
                System.out.println("❌ No record found for ID: " + ids);
            }

            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        sc.close();
    }
}
 