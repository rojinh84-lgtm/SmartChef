import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/smartchef_db";
    
   
    private static final String USER = "root";
    private static final String PASSWORD = "0151292868"; 

    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }
}
