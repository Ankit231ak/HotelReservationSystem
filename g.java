import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class g {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/image";
        String username = "root";
        String password = "90901212";
        String image_path = "A:\\Wallpaper\\Wallpaper#\\1363137.png";
        String query = "INSERT INTO image_table(data) VALUES(?)";

        // Load driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            return;
        }

        try (
            Connection con = DriverManager.getConnection(url, username, password);
            FileInputStream fis = new FileInputStream(image_path);
            PreparedStatement pstmt = con.prepareStatement(query)
        ) {
            pstmt.setBinaryStream(1, fis, fis.available());    //////
            int row = pstmt.executeUpdate();

            if (row > 0) {
                System.out.println("✅ Image Inserted Successfully");
            } else {
                System.out.println("❌ Image Insert Failed");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File read error: " + e.getMessage());
        }
    }
}
