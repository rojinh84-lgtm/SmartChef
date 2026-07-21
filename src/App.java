import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Copy Weekly Plan Test...");

        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        MealPlanDAO mealPlanDAO = new MealPlanDAO();

        // ۱. گرفتن تعداد برنامه‌های فعلی قبل از کپی
        List<MealPlan> beforeCopy = mealPlanDAO.getWeeklyMealPlan(currentUserId);
        System.out.println("Meal plans count before copy: " + beforeCopy.size());

        // ۲. اجرای عملیات کپی کردن برنامه هفته
        System.out.println("\n--- Copying Weekly Plan ---");
        boolean isCopied = mealPlanDAO.copyWeeklyPlan(currentUserId);

        // ۳. بررسی و چاپ برنامه جدید پس از کپی
        if (isCopied) {
            List<MealPlan> afterCopy = mealPlanDAO.getWeeklyMealPlan(currentUserId);
            System.out.println("\nMeal plans count after copy: " + afterCopy.size());
            
            System.out.println("\n================ UPDATED MEAL PLAN LIST ================");
            for (MealPlan plan : afterCopy) {
                System.out.println("🗓️  " + plan.getDayOfWeek() + " | " + plan.getMealType() + 
                                   " -> " + plan.getRecipeName() + " (Plan ID: " + plan.getId() + ")");
            }
            System.out.println("=======================================================");
        } else {
            System.out.println("Failed to copy meal plan! ❌");
        }
    }
}