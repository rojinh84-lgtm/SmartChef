import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    public boolean registerUser(User user) {
        //if user does not have username or password, return false
        if (user.getUsername() == null || user.getPassword() == null) {
            return false;
        }

        // 
        String checkUserQuery = "SELECT * FROM Users WHERE username = ?";
        String insertUserQuery = "INSERT INTO Users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";

        //connection to database and execute queries
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {
            
            
            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();  
            
            if (rs.next()) { 
                System.out.println("Username already exists!");
                return false; 
            }

            
            insertStmt.setString(1, user.getFirstName());
            insertStmt.setString(2, user.getLastName());
            insertStmt.setString(3, user.getUsername());
            insertStmt.setString(4, user.getPassword());

            
            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Database error during registration: " + e.getMessage());
            return false;
        }
    }
    public User loginUser(String username, String password) {
    
    String loginQuery = "SELECT * FROM Users WHERE username = ? AND password = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(loginQuery)) {
        
        
        
        stmt.setString(1, username);
        stmt.setString(2, password);
        
        ResultSet rs = stmt.executeQuery();
        
        // if username and password match, rs will have a result
        if (rs.next()) {
            //make a new User object and populate it with data from the result set
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("first_name")); 
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));

            System.out.println("Login successful! Welcome " + user.getFirstName());
            return user; //user found and returned
        }
        
    } catch (SQLException e) {
        System.out.println("Database error during login: " + e.getMessage());
    }
    
    System.out.println("Invalid username or password.");
    return null; //if no user found, return null or password is incorrect
}
}