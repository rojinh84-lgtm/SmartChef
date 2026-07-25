public class App {
    public static void main(String[] args) {
        System.out.println("--- Testing Weekly Nutrition Calculator ---");

        // ۱. معرفی کاربر و ابزار
        int currentUserId = 1;
        NutritionDAO nutritionDAO = new NutritionDAO();

        // ۲. فراخوانی متد محاسبه کل هفته
        Nutrition weeklyTotals = nutritionDAO.getWeeklyNutritionSum(currentUserId);

        // ۳. چاپ گزارش نهایی
        System.out.println("\n📊 Total Nutrition Report for the WHOLE WEEK 📊");
        System.out.println("🔥 Calories: " + weeklyTotals.getCalories() + " kcal");
        System.out.println("🥩 Protein:  " + weeklyTotals.getProtein() + " g");
        System.out.println("🍞 Carbs:    " + weeklyTotals.getCarbs() + " g");
        System.out.println("🥑 Fat:      " + weeklyTotals.getFat() + " g");
        System.out.println("---------------------------------------------");
    }
}