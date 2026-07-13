import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Test...");

        // ۱. شبیه‌سازی کاربر لاگین شده با شناسه 1
        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        RecipeDAO recipeDAO = new RecipeDAO();

        // ۲. گرفتن لیست غذاهای کاربر قبل از حذف
        System.out.println("\n--- Recipes BEFORE Delete ---");
        List<Recipe> recipes = recipeDAO.getRecipesByUserId(currentUserId);
        for (Recipe r : recipes) {
            System.out.println("ID: " + r.getId() + " | Name: " + r.getRecipeName());
        }

        // ۳. منطق انتخاب غذا برای حذف
        if (recipes.isEmpty()) {
            System.out.println("No recipes found to delete!");
        } else {
            int targetRecipeId;
            String targetName;

            // اگر بیشتر از ۱ غذا داشتیم، غذای دوم (ایندکس 1) را انتخاب کن
            if (recipes.size() > 1) {
                Recipe secondRecipe = recipes.get(1); // غذای دوم
                targetRecipeId = secondRecipe.getId();
                targetName = secondRecipe.getRecipeName();
                System.out.println("\nSelecting the second recipe for deletion: " + targetName + " (ID: " + targetRecipeId + ")");
            } else {
                // اگر فقط ۱ غذا داشتیم، همان اولی (ایندکس 0) را انتخاب کن
                Recipe firstRecipe = recipes.get(0); // غذای اول
                targetRecipeId = firstRecipe.getId();
                targetName = firstRecipe.getRecipeName();
                System.out.println("\nOnly one recipe found. Selecting it for deletion: " + targetName + " (ID: " + targetRecipeId + ")");
            }

            // ۴. اجرای متد حذف
            System.out.println("Deleting...");
            boolean deleteSuccess = recipeDAO.deleteRecipe(targetRecipeId);

            if (deleteSuccess) {
                System.out.println("Delete Test: PASSED! 🎉");
            } else {
                System.out.println("Delete Test: FAILED! ❌");
            }
        }

        // ۵. گرفتن لیست غذاها بعد از حذف برای اطمینان از پاک شدن آن
        System.out.println("\n--- Recipes AFTER Delete ---");
        List<Recipe> recipesAfter = recipeDAO.getRecipesByUserId(currentUserId);
        for (Recipe r : recipesAfter) {
            System.out.println("ID: " + r.getId() + " | Name: " + r.getRecipeName());
        }
    }
}