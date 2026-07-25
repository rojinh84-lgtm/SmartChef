public class App {
    public static void main(String[] args) {
        System.out.println("--- Testing Daily Nutrition Calculator ---");

        // ۱. معرفی کاربر و ابزار
        int currentUserId = 1;
        String targetDay = "Monday"; // روزی که می‌خواهیم کالری‌اش را حساب کنیم
        NutritionDAO nutritionDAO = new NutritionDAO();

        // ۲. فراخوانی متد محاسبه از دیتابیس
        Nutrition dailyTotals = nutritionDAO.getDailyNutritionSum(currentUserId, targetDay);

        // ۳. چاپ گزارش نهایی
        System.out.println("\n📊 Nutrition Report for " + targetDay + " 📊");
        System.out.println("🔥 Calories: " + dailyTotals.getCalories() + " kcal");
        System.out.println("🥩 Protein:  " + dailyTotals.getProtein() + " g");
        System.out.println("🍞 Carbs:    " + dailyTotals.getCarbs() + " g");
        System.out.println("🥑 Fat:      " + dailyTotals.getFat() + " g");
        System.out.println("---------------------------------------");
    }
}