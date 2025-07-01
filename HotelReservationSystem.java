import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/project1";
    private static final String username = "root";
    private static final String password = "90901212";

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            return;
        }

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            while (true) {
                System.out.println("\nHotel Management System");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1 -> reserveRoom(con, sc);
                    case 2 -> viewReservations(con);
                    case 3 -> getRoom(con, sc);
                    case 4 -> updateReservation(con, sc);
                    case 5 -> deleteReservation(con, sc);
                    case 0 -> {
                        exit();
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    static void reserveRoom(Connection con, Scanner sc) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = sc.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine(); 
            System.out.print("Enter contact number: ");
            String contactNumber = sc.nextLine();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, guestName);
                stmt.setInt(2, roomNumber);
                stmt.setString(3, contactNumber);

                int row = stmt.executeUpdate();
                System.out.println(row > 0 ? "Reservation Successful" : "Reservation Failed");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewReservations(Connection con) {
        String sql = "SELECT * FROM reservations";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nCurrent Reservations:");
            System.out.println("+----+--------------+------+----------------+--------------------------+");
            System.out.println("| ID | Guest Name   | Room | Contact Number | Reservation Date         |");
            System.out.println("+----+--------------+------+----------------+--------------------------+");

            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String guest = rs.getString("guest_name");
                int room = rs.getInt("room_number");
                String contact = rs.getString("contact_number");
                Timestamp date = rs.getTimestamp("reservation_date");

                System.out.printf("| %-2d | %-12s | %-4d | %-14s | %-24s |\n",
                        id, guest, room, contact, date.toString());
            }

            System.out.println("+----+--------------+------+----------------+--------------------------+");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void getRoom(Connection con, Scanner sc) {
        try {
            System.out.print("Enter reservation ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter guest name: ");
            String name = sc.nextLine();

            String sql = "SELECT room_number FROM reservations WHERE reservation_id = ? AND guest_name = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, name);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Room number is: " + rs.getInt("room_number"));
                    } else {
                        System.out.println("Reservation not found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateReservation(Connection con, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(con, id)) {
                System.out.println("Reservation not found.");
                return;
            }

            System.out.print("Enter new guest name: ");
            String name = sc.nextLine();
            System.out.print("Enter new room number: ");
            int room = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new contact number: ");
            String contact = sc.nextLine();

            String sql = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setInt(2, room);
                stmt.setString(3, contact);
                stmt.setInt(4, id);

                int row = stmt.executeUpdate();
                System.out.println(row > 0 ? "Update successful." : "Update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteReservation(Connection con, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int id = sc.nextInt();

            if (!reservationExists(con, id)) {
                System.out.println("Reservation not found.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int row = stmt.executeUpdate();
                System.out.println(row > 0 ? "Deleted successfully." : "Delete failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static boolean reservationExists(Connection con, int id) {
        String sql = "SELECT 1 FROM reservations WHERE reservation_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    static void exit() throws InterruptedException {
        System.out.print("Exiting");
        for (int i = 0; i < 5; i++) {
            Thread.sleep(500);
            System.out.print(".");
        }
        System.out.println("\nThank you for using the Hotel Reservation System!");
    }
}
