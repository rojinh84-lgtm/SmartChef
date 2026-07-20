import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Update & Delete Meal Plan Test...");

        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        RecipeDAO recipeDAO = new RecipeDAO();
        MealPlanDAO mealPlanDAO = new MealPlanDAO();

        List<Recipe> recipes = recipeDAO.getRecipesByUserId(currentUserId);

        if (recipes.size() >= 2) {
            Recipe secondRecipe = recipes.get(1); // انتخاب پیتزا

            // ۱. تست ویرایش: تغییر وعده شماره 1 به پیتزا برای روز Tuesday
            MealPlan planToUpdate = new MealPlan(1, currentUserId, secondRecipe.getId(), "Tuesday", "Dinner");
            System.out.println("\nTesting UPDATE on Meal Plan ID: 1...");
            boolean isUpdated = mealPlanDAO.addMealPlan(planToUpdate);

            if (isUpdated) {
                System.out.println("Update Test PASSED! 🎉");
            } else {
                System.out.println("Update Test FAILED! ❌");
            }

            // ۲. تست حذف: پاک کردن وعده شماره 1
            System.out.println("\nTesting DELETE on Meal Plan ID: 1...");
            boolean isDeleted = mealPlanDAO.deleteMealPlan(1, currentUserId);

            if (isDeleted) {
                System.out.println("Delete Test PASSED! 🎉");
            } else {
                System.out.println("Delete Test FAILED! ❌");
            }
        } else {
            System.out.println("Need at least 2 recipes in DB for this test!");
        }
    }
}