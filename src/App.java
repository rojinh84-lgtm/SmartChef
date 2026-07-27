public class App {
    public static void main(String[] args) {
        int currentUserId = 1;

        // --- تست قابلیت ۱: فیلتر محدودیت غذایی ---
        System.out.println("--- Testing Dietary Restrictions Filter ---");
        RecipeDAO recipeDAO = new RecipeDAO();
        // فرض می‌کنیم کاربر فقط غذاهای گیاهی می‌خواهد (true, false, false)
        java.util.List<Recipe> vegRecipes = recipeDAO.getFilteredRecipes(true, false, false);
        
        System.out.println("Vegetarian Recipes Found: " + vegRecipes.size());
        for (Recipe r : vegRecipes) {
            System.out.println("🌿 " + r.getRecipeName());
        }

        // --- تست قابلیت ۲: پیشنهاد خودکار برنامه ---
        System.out.println("\n--- Testing Auto Meal Plan Generation ---");
        MealPlanDAO mealPlanDAO = new MealPlanDAO();
        
        boolean isPlanGenerated = mealPlanDAO.generateAutoWeeklyPlan(currentUserId);
        
        if (isPlanGenerated) {
            System.out.println("\n✨ Auto Meal Plan generated successfully! ✨");
        } else {
            System.out.println("\n⚠️ There was an issue generating the full plan.");
        }
    }
}