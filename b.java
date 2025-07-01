import java.sql.*;

public class c {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{


        // to retrieve the database
        
        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "90901212";

        try {
            Class.forName("com.mysql.jdbc.Drivers");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from ankit;");

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int salary = rs.getInt("salary");

                System.out.println("\nId: " + id);
                System.out.println("Name: " + name);
                System.out.println("Salary: " + salary);
            }

            rs.close();
            stmt.close();
            con.close();
            System.out.println("\nConnection Closed Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
