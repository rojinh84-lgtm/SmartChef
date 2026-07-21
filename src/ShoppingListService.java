import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListService {
    
    private MealPlanDAO mealPlanDAO;
    private RecipeDAO recipeDAO;

    public ShoppingListService() {
        this.mealPlanDAO = new MealPlanDAO();
        this.recipeDAO = new RecipeDAO();
    }

    public List<Ingredient> generateWeeklyShoppingList(int userId) {
        List<MealPlan> weeklyPlans = mealPlanDAO.getWeeklyMealPlan(userId);
        Map<String, Ingredient> aggregatedIngredients = new HashMap<>();

        for (MealPlan plan : weeklyPlans) {
            int recipeId = plan.getRecipeId();
            
            // اصلاح شد: فراخوانی متد جدید از RecipeDAO
            Recipe recipe = recipeDAO.getRecipeById(recipeId); 
            
            if (recipe != null && recipe.getIngredients() != null) {
                for (Ingredient ing : recipe.getIngredients()) {
                    String key = ing.getIngredientName().trim().toLowerCase() + "_" + ing.getUnit().trim().toLowerCase();
                    
                    if (aggregatedIngredients.containsKey(key)) {
                        Ingredient existingIng = aggregatedIngredients.get(key);
                        
                        existingIng.setAmount(existingIng.getAmount() + ing.getAmount());
                    } else {
                        Ingredient newIng = new Ingredient();
                        newIng.setIngredientName(ing.getIngredientName());
                        newIng.setAmount(ing.getAmount());
                        newIng.setUnit(ing.getUnit());
                        
                        aggregatedIngredients.put(key, newIng);
                    }
                }
            }
        }
        
        return new ArrayList<>(aggregatedIngredients.values());
    }
}