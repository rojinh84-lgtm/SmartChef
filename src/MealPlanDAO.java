import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public boolean deleteMealPlan(int mealPlanId, int userId) {
    String deleteQuery = "DELETE FROM MealPlans WHERE id = ? AND user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

        stmt.setInt(1, mealPlanId);
        stmt.setInt(2, userId);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Meal plan deleted successfully! 🗑️");
            return true;
        }

    } catch (SQLException e) {
        System.out.println("Database error while deleting meal plan: " + e.getMessage());
    }
    return false;
}
public List<MealPlan> getWeeklyMealPlan(int userId) {
    List<MealPlan> weeklyPlan = new ArrayList<>();
    
    // use JOIN to fetch recipe names along with meal plans
    String query = "SELECT mp.id, mp.user_id, mp.recipe_id, mp.day_of_week, mp.meal_type, r.recipe_name " +
                   "FROM MealPlans mp " +
                   "JOIN Recipes r ON mp.recipe_id = r.id " +
                   "WHERE mp.user_id = ? " +
                   "ORDER BY FIELD(mp.day_of_week, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, userId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MealPlan plan = new MealPlan();
                plan.setId(rs.getInt("id"));
                plan.setUserId(rs.getInt("user_id"));
                plan.setRecipeId(rs.getInt("recipe_id"));
                plan.setRecipeName(rs.getString("recipe_name"));
                plan.setDayOfWeek(rs.getString("day_of_week"));
                plan.setMealType(rs.getString("meal_type"));

                weeklyPlan.add(plan);
            }
        }

    } catch (SQLException e) {
        System.out.println("Database error while fetching weekly meal plan: " + e.getMessage());
    }
    return weeklyPlan;
}

public boolean copyWeeklyPlan(int userId) {
    //get all current meal plans for the user
    List<MealPlan> currentPlans = getWeeklyMealPlan(userId);

    if (currentPlans.isEmpty()) {
        System.out.println("No meal plans to copy!");
        return false;
    }

    boolean allCopied = true;

    //repeat the current plans for the next week
    for (MealPlan plan : currentPlans) {
       MealPlan newPlan = new MealPlan();
           newPlan.setUserId(userId);
           newPlan.setRecipeId(plan.getRecipeId());
           newPlan.setDayOfWeek(plan.getDayOfWeek());
           newPlan.setMealType(plan.getMealType());
            

            boolean success = addMealPlan(newPlan);
            if (!success) {
                allCopied = false;
            }
        }

    if (allCopied) {
        System.out.println("Weekly plan copied successfully! 📋✨");
    }
    return allCopied;
}
public boolean generateAutoWeeklyPlan(int userId) {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        RecipeDAO recipeDAO = new RecipeDAO();
        List<Recipe> allRecipes = recipeDAO.getRecipesByUserId(userId); 
        
        if (allRecipes.isEmpty()) {
            System.out.println("No recipes available to generate a plan! ❌");
            return false;
        }

        Random random = new Random();
        boolean allSuccess = true;

        System.out.println("Generating Auto Meal Plan... 🔄");
        
        for (String day : daysOfWeek) {
            int randomIndex = random.nextInt(allRecipes.size());
            Recipe randomRecipe = allRecipes.get(randomIndex);
            
            MealPlan newPlan = new MealPlan();
            newPlan.setUserId(userId);
            newPlan.setRecipeId(randomRecipe.getId());
            newPlan.setDayOfWeek(day);
            newPlan.setMealType("Lunch");
            boolean isAdded = addMealPlan(newPlan); 
            
            if (!isAdded) {
                allSuccess = false;
            } else {
                System.out.println("✔️ Added '" + randomRecipe.getRecipeName() + "' for " + day);
            }
        }
        
        return allSuccess;
    }
}
