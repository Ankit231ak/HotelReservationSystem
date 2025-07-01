import java.sql.*;

public class c {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{


        // to retrieve the database
        
        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "90901212";
        String query = "insert into ankit(id, name, salary) values(3, 'Manoj', 30000);";

        try {
            Class.forName("com.mysql.jdbc.Drivers");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            int row = stmt.executeUpdate(query);

            if(row>0){
                System.out.println("Insert Successfully: " + row);
            }else{
                System.out.println("Fail: " + row);
            }

            stmt.close();
            con.close();
            System.out.println("\nConnection Closed Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
