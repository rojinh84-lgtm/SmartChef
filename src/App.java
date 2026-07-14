import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Update Test...");

        // ۱. شبیه‌سازی کاربر لاگین شده با شناسه 1
        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        RecipeDAO recipeDAO = new RecipeDAO();

        // ۲. گرفتن لیست غذاها برای پیدا کردن یک غذا جهت ویرایش
        List<Recipe> recipes = recipeDAO.getRecipesByUserId(currentUserId);
        
        if (recipes.isEmpty()) {
            System.out.println("No recipes found to update!");
        } else {
            // ۳. انتخاب اولین غذای لیست به عنوان یک شیء
            Recipe recipeToUpdate = recipes.get(0);
            
            System.out.println("\n--- Recipe BEFORE Update ---");
            System.out.println("ID: " + recipeToUpdate.getId());
            System.out.println("Name: " + recipeToUpdate.getRecipeName());
            System.out.println("Prep Time: " + recipeToUpdate.getPreparationTime() + " mins");
            System.out.println("Instructions: " + recipeToUpdate.getInstructions());

            // ۴. تغییر ویژگی‌های شیء با استفاده از Setterها (منطق شی‌گرایی)
            System.out.println("\nModifying the recipe object in Java...");
            recipeToUpdate.setRecipeName(recipeToUpdate.getRecipeName() + " (Spicy Version)");
            recipeToUpdate.setPreparationTime(recipeToUpdate.getPreparationTime() + 5); // ۵ دقیقه اضافه می‌کنیم
            recipeToUpdate.setInstructions(recipeToUpdate.getInstructions() + " Add chili flakes at the end.");

            // ۵. فرستادن کل شیء تغییر یافته به دیتابیس برای ذخیره نهایی
            System.out.println("Sending updated object to database...");
            boolean updateSuccess = recipeDAO.updateRecipe(recipeToUpdate);

            if (updateSuccess) {
                System.out.println("Update Test: PASSED! 🎉");
            } else {
                System.out.println("Update Test: FAILED! ❌");
            }

            // ۶. چک کردن نتیجه نهایی با گرفتن دوباره اطلاعات از دیتابیس
            System.out.println("\n--- Recipe AFTER Update (Verified from DB) ---");
            List<Recipe> updatedRecipes = recipeDAO.getRecipesByUserId(currentUserId);
            Recipe verifiedRecipe = updatedRecipes.get(0); // دوباره اولین غذا را نگاه میکنیم
            System.out.println("ID: " + verifiedRecipe.getId());
            System.out.println("Name: " + verifiedRecipe.getRecipeName());
            System.out.println("Prep Time: " + verifiedRecipe.getPreparationTime() + " mins");
            System.out.println("Instructions: " + verifiedRecipe.getInstructions());
        }
    }
}