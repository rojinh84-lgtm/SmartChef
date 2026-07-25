import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NutritionDAO {
    
    //method to save or update nutrition information for a recipe
    public boolean saveOrUpdateNutrition(Nutrition nutrition) {
        //using ON DUPLICATE KEY UPDATE to handle both insert and update in one query
        String query = "INSERT INTO NutritionInfo (recipe_id, calories, protein, carbs, fat) " +
                       "VALUES (?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE calories = ?, protein = ?, carbs = ?, fat = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // values for INSERT
            stmt.setInt(1, nutrition.getRecipeId());
            stmt.setDouble(2, nutrition.getCalories());
            stmt.setDouble(3, nutrition.getProtein());
            stmt.setDouble(4, nutrition.getCarbs());
            stmt.setDouble(5, nutrition.getFat());
            
            // values for UPDATE (if exists)
            stmt.setDouble(6, nutrition.getCalories());
            stmt.setDouble(7, nutrition.getProtein());
            stmt.setDouble(8, nutrition.getCarbs());
            stmt.setDouble(9, nutrition.getFat());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Database error while saving nutrition info: " + e.getMessage());
            return false;
        }
    }
}