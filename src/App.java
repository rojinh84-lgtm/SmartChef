import java.util.List;
public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Category Search Test...");

        // ۱. شبیه‌سازی کاربر لاگین شده با شناسه 1
        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        RecipeDAO recipeDAO = new RecipeDAO();

        // ۲. تست اول: جستجو در دسته‌بندی‌هایی که شامل کلمه "din" هستند (Dinner)
        String testCategory1 = "din";
        System.out.println("\n--- Testing Category Search for: '" + testCategory1 + "' ---");
        List<Recipe> result1 = recipeDAO.searchRecipesByCategory(testCategory1, currentUserId);
        
        if (result1.isEmpty()) {
            System.out.println("No recipes found in category containing: " + testCategory1);
        } else {
            System.out.println("Found " + result1.size() + " recipe(s) in this category:");
            for (Recipe r : result1) {
                System.out.println("- ID: " + r.getId() + " | Name: " + r.getRecipeName() + " | Category: " + r.getCategory());
            }
        }

        // ۳. تست دوم: جستجوی یک دسته‌بندی که نداریم (Lunch)
        String testCategory2 = "Lunch";
        System.out.println("\n--- Testing Category Search for: '" + testCategory2 + "' ---");
        List<Recipe> result2 = recipeDAO.searchRecipesByCategory(testCategory2, currentUserId);
        
        if (result2.isEmpty()) {
            System.out.println("No recipes found in category containing: " + testCategory2 + " (This is correct!)");
        } else {
            System.out.println("Found " + result2.size() + " recipe(s):");
            for (Recipe r : result2) {
                System.out.println("- Name: " + r.getRecipeName());
            }
        }
    }
}