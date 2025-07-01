import java.sql.*;

public class d {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{


        // to delete the database
        
        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "90901212";
        String query = "Delete from ankit where id = 3;";

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
                System.out.println("Delete Successfully: " + row);
            }else{
                System.out.println("Delete Fail: " + row);
            }

            stmt.close();
            con.close();
            System.out.println("\nConnection Closed Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
