import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MealPlanDAO {
    public boolean addMealPlan(MealPlan mealPlan) {
        String insertQuery = "INSERT INTO MealPlans (user_id, recipe_id, day_of_week, meal_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, mealPlan.getUserId());
            stmt.setInt(2, mealPlan.getRecipeId());
            stmt.setString(3, mealPlan.getDayOfWeek());
            stmt.setString(4, mealPlan.getMealType());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                //Retrieve the generated ID and set it in the mealPlan object
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mealPlan.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Meal plan added successfully! 📅");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Database error while adding meal plan: " + e.getMessage());
        }
        return false;
    }
}
