import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting SmartChef Add Meal Plan Test...");

       
        User mockUser = new User(1, "rozhin_h", "Rozhin", "Hasadi", "pass");
        UserSession.login(mockUser);
        int currentUserId = UserSession.getCurrentUserId();

        RecipeDAO recipeDAO = new RecipeDAO();
        MealPlanDAO mealPlanDAO = new MealPlanDAO();

        // get recipes for the current user
        List<Recipe> recipes = recipeDAO.getRecipesByUserId(currentUserId);

        if (recipes.isEmpty()) {
            System.out.println("No recipes found to add to meal plan!");
        } else {
            
            Recipe selectedRecipe = recipes.get(0);

            // creating a new meal plan for Monday Dinner with the selected recipe
            MealPlan newPlan = new MealPlan(0, currentUserId, selectedRecipe.getId(), "Monday", "Dinner");

            System.out.println("\nAdding meal plan: " + selectedRecipe.getRecipeName() + " for Monday Dinner...");
            
            //add to database
            boolean isAdded = mealPlanDAO.addMealPlan(newPlan);

            if (isAdded) {
                System.out.println("Test PASSED! Generated MealPlan ID: " + newPlan.getId());
            } else {
                System.out.println("Test FAILED! Could not add meal plan.");
            }
        }
    }
}