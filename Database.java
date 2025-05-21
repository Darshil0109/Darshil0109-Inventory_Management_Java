import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/inventory";
    private static final String USER = "postgres";
    private static final String PASS = "postgres"; // Change this to your password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: PostgreSQL JDBC Driver not found!");
            System.exit(1);
            return null;
        }
    }
} 