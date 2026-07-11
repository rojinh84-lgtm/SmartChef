public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Transaction Test...");

        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        //make a new recipe object for the pizza
        Recipe pizza = new Recipe(0, currentUserId, "Margherita Pizza", "Dinner", 4, "1. Roll dough. 2. Add tomato sauce and cheese. 3. Bake.", 15);

        //making a list of ingredients for the pizza
        java.util.List<Ingredient> pizzaIngredients = new java.util.ArrayList<>();
        
        Ingredient ing1 = new Ingredient();
        ing1.setIngredientName("Pizza Dough");
        ing1.setAmount(1.0);
        ing1.setUnit("pieces");
        pizzaIngredients.add(ing1);

        Ingredient ing2 = new Ingredient();
        ing2.setIngredientName("Tomato Sauce");
        ing2.setAmount(0.5);
        ing2.setUnit("cups");
        pizzaIngredients.add(ing2);

        Ingredient ing3 = new Ingredient();
        ing3.setIngredientName("Mozzarella Cheese");
        ing3.setAmount(200.0);
        ing3.setUnit("grams");
        pizzaIngredients.add(ing3);
        // run the transaction test
        RecipeDAO recipeDAO = new RecipeDAO();
        
        System.out.println("\n--- Testing Transaction Add ---");
        boolean success = recipeDAO.addRecipeWithIngredients(pizza, pizzaIngredients);

        if (success) {
            System.out.println("Transaction Test: PASSED! 🎉");
        } else {
            System.out.println("Transaction Test: FAILED! ❌");
        }
    }
}