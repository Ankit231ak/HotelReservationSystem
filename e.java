import java.sql.*;

public class e {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{


        // to Update the database
        
        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "90901212";
        String query = "Update ankit set name = 'Rohan2' where id = 2;";

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
                System.out.println("Update Successfully: " + row);
            }else{
                System.out.println("Update Fail: " + row);
            }

            stmt.close();
            con.close();
            System.out.println("\nConnection Closed Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
