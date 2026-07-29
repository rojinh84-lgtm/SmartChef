import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class App {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("  👨‍🍳 Welcome to SmartChef App 👩‍🍳  ");
        System.out.println("=====================================");
        
        int currentUserId = 1; // For demonstration, we assume the user is logged in and has an ID of 1.

        boolean running = true;
        while(running){
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. 🍲 Recipe Management");
            System.out.println("2. 📅 Meal Planning");
            System.out.println("3. 🛒 Shopping List");
            System.out.println("4. 📊 Nutrition & Health");
            System.out.println("5. ❌ Exit");
            System.out.println("\nPlease choose an option:");
            int choice = input.nextInt();
            input.nextLine();
            switch(choice){
                case 1:
                    System.out.println("You selected Recipe Management.🍳");
                    
                    System.out.println("\n--- 🍲 Recipe Management Menu ---");
                    System.out.println("1. Add a New Recipe");
                    System.out.println("2. View All My Recipes");
                    System.out.println("3. Update a Recipe");
                    System.out.println("4. Delete a Recipe");
                    System.out.println("5. Search Recipes ");
                    System.out.println("6. Filter by Dietary Restrictions (for example: vegetarian, gluten-free, lactose-free)");
                    System.out.println("7. 🔙 Back to Main Menu ");
                    System.out.print("Please choose an option (1-7): ");
                    int choiceForRecipeManagement = input.nextInt();
                    input.nextLine();
                    switch (choiceForRecipeManagement) {
                        case 1:
                            // Add a new recipe
                            Recipe newRecipe = new Recipe();
                            newRecipe.setUserId(currentUserId);
                            
                            System.out.print("Enter recipe name: ");
                            newRecipe.setRecipeName(input.nextLine());
                            
                            System.out.print("Enter category (e.g., Breakfast, Lunch, Dessert): ");
                            newRecipe.setCategory(input.nextLine());
                            
                            System.out.print("Enter servings (number of people): ");
                            newRecipe.setServing(input.nextInt());
                            input.nextLine(); 
                            
                            System.out.print("Enter preparation time (in minutes): ");
                            newRecipe.setPreparationTime(input.nextInt());
                            input.nextLine(); 
                            
                            System.out.print("Enter .: ");
                            newRecipe.setInstructions(input.nextLine());

                            ArrayList<Ingredient> ingredients = new ArrayList<>();
                            System.out.println("\n--- Adding Ingredients ---");
                            
                            while (true) {
                                System.out.print("Enter ingredient name (or type 'done' to finish): ");
                                String ingredientName = input.nextLine();
                                
                                if (ingredientName.equalsIgnoreCase("done")){
                                    break;
                                }
                                
                                System.out.print("Enter amount for " + ingredientName + " (e.g., 2.5): ");
                                double amount = input.nextDouble();
                                input.nextLine(); 
                                
                                System.out.print("Enter unit for " + ingredientName + " (e.g., grams, cups, pieces): ");
                                String unit = input.nextLine();
                                
                                Ingredient newIngredient = new Ingredient();
                                newIngredient.setIngredientName(ingredientName);
                                newIngredient.setAmount(amount);
                                newIngredient.setUnit(unit);
                                
                                ingredients.add(newIngredient);
                            }

                            RecipeDAO recipeDAO = new RecipeDAO();
                            boolean isSuccess = recipeDAO.addRecipeWithIngredients(newRecipe, ingredients);
                            
                            if (isSuccess) {
                                System.out.println("\n✅ Recipe '" + newRecipe.getRecipeName() + "' added successfully!");
                            } else {
                                System.out.println("\n❌ Failed to add the recipe. Please try again.");
                            }
                            

                            break;
                        case 2:
                            // View all my recipes
                            System.out.println("\n--- 📖 My Recipes ---");
                            RecipeDAO viewRecipeDAO = new RecipeDAO();
                            
                            //take all recipes of the current user from database
                            java.util.List<Recipe> myRecipes = viewRecipeDAO.getRecipesByUserId(currentUserId);
                            
                            if (myRecipes.isEmpty()) {
                                System.out.println("You don't have any recipes yet. Let's add some! 👨‍🍳");
                            } else {
                                //print all recipes of the current user
                                for (Recipe r : myRecipes) {
                                    System.out.println("ID: " + r.getId() + 
                                                       " | Name: " + r.getRecipeName() + 
                                                       " | Category: " + r.getCategory() + 
                                                       " | Prep Time: " + r.getPreparationTime() + " mins" +
                                                       " | Servings: " + r.getServing());
                                }
                            }
                            break;
                        case 3:
                            // Update a recipe
                            System.out.println("\n--- ✏️ Update Recipe ---");
                            System.out.print("Enter the ID of the recipe you want to update (see 'View All My Recipes'): ");
                            int updateId = input.nextInt();
                            input.nextLine(); 
                            
                            Recipe updatedRecipe = new Recipe();
                            updatedRecipe.setId(updateId);
                            
                            System.out.print("Enter NEW recipe name: ");
                            updatedRecipe.setRecipeName(input.nextLine());
                            
                            System.out.print("Enter NEW category (e.g., Breakfast, Lunch): ");
                            updatedRecipe.setCategory(input.nextLine());
                            
                            System.out.print("Enter NEW servings: ");
                            updatedRecipe.setServing(input.nextInt());
                            input.nextLine(); 
                            
                            System.out.print("Enter NEW preparation time (mins): ");
                            updatedRecipe.setPreparationTime(input.nextInt());
                            input.nextLine(); 
                            
                            System.out.print("Enter NEW instructions: ");
                            updatedRecipe.setInstructions(input.nextLine());
                            
                            RecipeDAO updateDAO = new RecipeDAO();
                            boolean isUpdated = updateDAO.updateRecipe(updatedRecipe);
                            
                            if (isUpdated) {
                                System.out.println("✅ Recipe updated successfully!");
                            } else {
                                System.out.println("❌ Update failed! Are you sure the ID is correct?");
                            }
                            break;

                        case 4:
                            // Delete a recipe
                            System.out.println("\n--- 🗑️ Delete Recipe ---");
                            System.out.print("Enter the ID of the recipe you want to delete: ");
                            int deleteId = input.nextInt();
                            input.nextLine(); 
                            System.out.print("⚠️ Are you sure you want to delete recipe ID " + deleteId + "? (yes/no): ");
                            String confirm = input.nextLine();
                            
                            if (confirm.equalsIgnoreCase("yes")) {
                                RecipeDAO deleteDAO = new RecipeDAO();
                                boolean isDeleted = deleteDAO.deleteRecipe(deleteId);
                                
                                if (isDeleted) {
                                    System.out.println("✅ Recipe and its ingredients deleted successfully!");
                                } else {
                                    System.out.println("❌ Deletion failed! Maybe the ID doesn't exist.");
                                }
                            } else {
                                System.out.println("Deletion cancelled. 🛑");
                            }
                            break;
                        case 5:
                            // Search recipes
                            System.out.println("\n--- 🔍 Search Recipes ---");
                            System.out.println("1. Search by Name ");
                            System.out.println("2. Search by Category ");
                            System.out.println("3. Search by Ingredient ");
                            System.out.print("Choose search type (1-3): ");
                            
                            int searchType = input.nextInt();
                            input.nextLine(); 
                            
                            System.out.print("Enter your keyword : ");
                            String keyword = input.nextLine();
                            
                            RecipeDAO searchDAO = new RecipeDAO();
                            java.util.List<Recipe> searchResults = new ArrayList<>();
                            
                            if (searchType == 1) {
                                searchResults = searchDAO.searchRecipesByName(keyword, currentUserId);
                            } else if (searchType == 2) {
                                searchResults = searchDAO.searchRecipesByCategory(keyword, currentUserId);
                            } else if (searchType == 3) {
                                searchResults = searchDAO.searchRecipesByIngredient(keyword, currentUserId);
                            } else {
                                System.out.println("⚠️ Invalid search type!");
                            }
                            
                            if (searchResults.isEmpty()) {
                                System.out.println("No recipes found matching '" + keyword + "'. 😔");
                            } else {
                                System.out.println("\n--- 🎯 Search Results ---");
                                for (Recipe r : searchResults) {
                                    System.out.println("ID: " + r.getId() + " | Name: " + r.getRecipeName() + " | Category: " + r.getCategory());
                                }
                            }
                            break;

                        case 6:
                            // Filter by dietary restrictions
                            System.out.println("\n--- 🥗 Dietary Restrictions Filter ---");
                            System.out.println("Please enter 'true' or 'false' for the following:");
                            
                            System.out.print("Vegetarian only? (true/false): ");
                            boolean isVeg = input.nextBoolean();
                            
                            System.out.print("Gluten-free only? (true/false): ");
                            boolean isGluten = input.nextBoolean();
                            
                            System.out.print("Lactose-free only? (true/false): ");
                            boolean isLactose = input.nextBoolean();
                            input.nextLine();
                            
                            RecipeDAO filterDAO = new RecipeDAO();
                            java.util.List<Recipe> filteredList = filterDAO.getFilteredRecipes(isVeg, isGluten, isLactose);
                            
                            if (filteredList.isEmpty()) {
                                System.out.println("No recipes match your dietary restrictions.");
                            } else {
                                System.out.println("\n--- 🌿 Filtered Recipes ---");
                                for (Recipe r : filteredList) {
                                    System.out.println("ID: " + r.getId() + " | Name: " + r.getRecipeName());
                                }
                            }
                            break;

                        case 7:
                            // Back to main menu
                            System.out.println("Returning to Main Menu... 🔙");
                            break;
                    
                        default:
                            break;
                    }

                    // i will compelete this part later for recipe management
                    break;
                case 2:
                    System.out.println("You selected Meal Planning.📅");
                    System.out.println("\n--- 📅 Meal Planning Menu ---");
                    System.out.println("1. Add a Meal to Plan ");
                    System.out.println("2. View Weekly Meal Plan");
                    System.out.println("3. Generate Auto Weekly Plan ");
                    System.out.println("4. 🔙 Back to Main Menu");
                    System.out.print("Please choose an option (1-4): ");
                    
                    int choiceForMealPlanning = input.nextInt();
                    input.nextLine(); 
                    
                    switch (choiceForMealPlanning) {
                        case 1:
                            // Add a meal to plan
                            System.out.println("\n--- ➕ Add Meal to Plan ---");
                            System.out.print("Enter Recipe ID to add (you can find this in Recipe Management): ");
                            int recipeId = input.nextInt();
                            input.nextLine(); 
                            
                            System.out.print("Enter Day of Week (e.g., Monday, Tuesday): ");
                            String day = input.nextLine();
                            
                            System.out.print("Enter Meal Type (e.g., Breakfast, Lunch, Dinner): ");
                            String mealType = input.nextLine();

                            MealPlan newPlan = new MealPlan();
                            newPlan.setUserId(currentUserId);
                            newPlan.setRecipeId(recipeId);
                            newPlan.setDayOfWeek(day);
                            newPlan.setMealType(mealType);

                            MealPlanDAO addPlanDAO = new MealPlanDAO();
                            
                            addPlanDAO.addMealPlan(newPlan);
                            break;

                        case 2:
                            // View weekly meal plan
                            System.out.println("\n--- 📅 My Weekly Meal Plan ---");
                            MealPlanDAO viewPlanDAO = new MealPlanDAO();
                            
                            List<MealPlan> myPlan = viewPlanDAO.getWeeklyMealPlan(currentUserId);
                            
                            if (myPlan.isEmpty()) {
                                System.out.println("Your meal plan is empty! Let's add some meals. 🍽️");
                            } else {
                                
                                for (MealPlan plan : myPlan) {
                                    System.out.println("Plan ID: " + plan.getId() + 
                                                       " | Day: " + plan.getDayOfWeek() + 
                                                       " | Meal: " + plan.getMealType() + 
                                                       " | Recipe: " + plan.getRecipeName());
                                }
                            }
                            break;

                        case 3:
                            // Generate auto weekly plan
                            System.out.println("\n--- 🤖 Auto-Generate Weekly Plan ---");
                            MealPlanDAO autoPlanDAO = new MealPlanDAO();
                            // call the method to generate auto weekly plan for the current user
                            autoPlanDAO.generateAutoWeeklyPlan(currentUserId);
                            break;

                        case 4:
                            // Back to main menu
                            System.out.println("Returning to Main Menu... 🔙");
                            break;
                    
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("You selected Shopping List.🛒");
                    System.out.println("\n--- 🛒 Shopping List Menu ---");
                    System.out.println("1. Generate Shopping List");
                    System.out.println("2. View Shopping List ");
                    System.out.println("3. Mark Item as Purchased ");
                    System.out.println("4. Remove Item from List");
                    System.out.println("5. 🔙 Back to Main Menu");
                    System.out.print("Please choose an option (1-5): ");
                    System.out.println("You selected Shopping List.🛒");
                    int choiceForShoppingList = input.nextInt();
                    input.nextLine();
                    switch (choiceForShoppingList) {
                        case 1:
                            // Generate shopping list
                            break;
                        case 2:
                            // View shopping list
                            break;
                        case 3:
                            // Mark item as purchased
                            break;
                        case 4:
                            // Remove item from list
                            break;
                        case 5:
                            // Back to main menu
                            break;
                    
                        default:
                            break;
                    }
                    // i will compelete this part later for shopping list
                    break;
                case 4:
                    System.out.println("You selected Nutrition & Health.📊");
                    
                    System.out.println("\n--- 📊 Nutrition & Health Menu ---");
                    System.out.println("1. Add Nutrition Info to a Recipe ");
                    System.out.println("2. View Daily Nutrition Sum");
                    System.out.println("3. View Weekly Nutrition Sum");
                    System.out.println("4. 🔙 Back to Main Menu");
                    System.out.print("Please choose an option (1-4): ");
                    int choiceForNutritionHealth = input.nextInt();
                    input.nextLine();
                    switch (choiceForNutritionHealth) {
                        case 1:
                            // Add nutrition info to a recipe
                            break;
                        case 2:
                            // View daily nutrition sum
                            break;
                        case 3:
                            // View weekly nutrition sum
                            break;
                        case 4:
                            // Back to main menu
                            break;
                    
                        default:
                            break;
                    }
                        
                    // i will compelete this part later for nutrition & health
                    break;
                case 5:
                    System.out.println("Thank you for using SmartChef! Goodbye! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!! Please try again.");
            }
        }
        input.close();
    }
}