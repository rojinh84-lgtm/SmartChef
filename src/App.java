import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Shopping List Aggregation Test...");

        // شبیه‌سازی لاگین
        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        ShoppingListService shoppingListService = new ShoppingListService();

        System.out.println("\nGenerating your smart shopping list for the week... 🛒\n");
        List<Ingredient> shoppingList = shoppingListService.generateWeeklyShoppingList(currentUserId);

        if (shoppingList.isEmpty()) {
            System.out.println("Your meal plan is empty or recipes have no ingredients!");
        } else {
            System.out.println("================ YOUR SMART SHOPPING LIST ================");
            for (Ingredient ing : shoppingList) {
                System.out.println("🔸 " + ing.getIngredientName() + " : " + ing.getAmount() + " " + ing.getUnit());
            }
            System.out.println("==========================================================");
        }
    }
}