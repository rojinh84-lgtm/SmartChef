import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class NutritionDAO extends BaseDAO {
    
    //method to save or update nutrition information for a recipe
    public boolean saveOrUpdateNutrition(Nutrition nutrition) {
        //using ON DUPLICATE KEY UPDATE to handle both insert and update in one query
        String query = "INSERT INTO NutritionInfo (recipe_id, calories, protein, carbs, fat) " +
                       "VALUES (?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE calories = ?, protein = ?, carbs = ?, fat = ?";
        
        try (Connection conn = getConnection();
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
    // متد کمکی: گرفتن اطلاعات تغذیه‌ای فقط برای یک دستورپخت خاص
    public Nutrition getNutritionByRecipeId(int recipeId) {
        String query = "SELECT * FROM NutritionInfo WHERE recipe_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, recipeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Nutrition n = new Nutrition();
                    n.setCalories(rs.getDouble("calories"));
                    n.setProtein(rs.getDouble("protein"));
                    n.setCarbs(rs.getDouble("carbs"));
                    n.setFat(rs.getDouble("fat"));
                    return n;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching nutrition: " + e.getMessage());
        }
        return null;
    }

    //method to get the total nutrition for a user on a specific day of the week
    public Nutrition getDailyNutritionSum(int userId, String dayOfWeek) {
        //to sum up the nutrition values for the day, we initialize a Nutrition object with zero values
        Nutrition dailyTotals = new Nutrition();
        dailyTotals.setCalories(0);
        dailyTotals.setProtein(0);
        dailyTotals.setCarbs(0);
        dailyTotals.setFat(0);

        //to get the meal plans for the user
        MealPlanDAO mealPlanDAO = new MealPlanDAO();
        java.util.List<MealPlan> weeklyPlans = mealPlanDAO.getWeeklyMealPlan(userId);

        //for moving through each meal plan and summing up the nutrition values for the specified day
        for (MealPlan plan : weeklyPlans) {

            if (plan.getDayOfWeek().equalsIgnoreCase(dayOfWeek)) {

                Nutrition info = getNutritionByRecipeId(plan.getRecipeId());
                //if nutrition info exists for the recipe, we add it to the daily totals
                if (info != null) {
                    dailyTotals.setCalories(dailyTotals.getCalories() + info.getCalories());
                    dailyTotals.setProtein(dailyTotals.getProtein() + info.getProtein());
                    dailyTotals.setCarbs(dailyTotals.getCarbs() + info.getCarbs());
                    dailyTotals.setFat(dailyTotals.getFat() + info.getFat());
                }
            }
        }
        
        return dailyTotals;
    }
    // method to get the total nutrition for a user for the entire week
    public Nutrition getWeeklyNutritionSum(int userId) {
        
        Nutrition weeklyTotals = new Nutrition();
        weeklyTotals.setCalories(0);
        weeklyTotals.setProtein(0);
        weeklyTotals.setCarbs(0);
        weeklyTotals.setFat(0);

        //to get the meal plans for the user(for the whole week)
        MealPlanDAO mealPlanDAO = new MealPlanDAO();
        java.util.List<MealPlan> weeklyPlans = mealPlanDAO.getWeeklyMealPlan(userId);

        // moving through each meal plan and summing up the nutrition values for the week
        for (MealPlan plan : weeklyPlans) {

            Nutrition info = getNutritionByRecipeId(plan.getRecipeId());
            
            //summing up
            if (info != null) {
                weeklyTotals.setCalories(weeklyTotals.getCalories() + info.getCalories());
                weeklyTotals.setProtein(weeklyTotals.getProtein() + info.getProtein());
                weeklyTotals.setCarbs(weeklyTotals.getCarbs() + info.getCarbs());
                weeklyTotals.setFat(weeklyTotals.getFat() + info.getFat());
            }
        }
        
        return weeklyTotals;
    }
}