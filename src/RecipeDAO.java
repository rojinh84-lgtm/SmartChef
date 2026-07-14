import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// here in this class i use ai to make a transaction that will add a recipe and all its ingredients in one go. if any part of the process fails, the entire transaction will be rolled back to maintain data integrity.re
public class RecipeDAO {
    public boolean addRecipe(Recipe recipe) {
        String insertQuery = "INSERT INTO Recipes (user_id, recipe_name, category, servings, instructions, preparation_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(insertQuery)){
            stmt.setInt(1, recipe.getUserId());
            stmt.setString(2, recipe.getRecipeName());
            stmt.setString(3, recipe.getCategory());
            stmt.setInt(4, recipe.getServing());
            stmt.setString(5, recipe.getInstructions());
            stmt.setInt(6, recipe.getPreparationTime());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
           
        } catch (Exception e) {
            System.out.println("Database error while adding recipe: " + e.getMessage());
            return false;
        }}

    public List<Recipe> getRecipesByUserId(int userId) {
        List<Recipe> recipeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Recipes WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            
            stmt.setInt(1, userId);
            

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recipe recipe = new Recipe();
                
                recipe.setId(rs.getInt("id"));
                recipe.setUserId(rs.getInt("user_id"));
                
                recipe.setRecipeName(rs.getString("recipe_name")); 
                recipe.setCategory(rs.getString("category"));
                recipe.setServing(rs.getInt("servings"));
                recipe.setInstructions(rs.getString("instructions"));
                recipe.setPreparationTime(rs.getInt("preparation_time"));

                //finally, add the recipe to the list
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            System.out.println("Database error while fetching recipes: " + e.getMessage());
        }

        return recipeList;// this will return all the recipes for the given userId, or an empty list if none are found  
    }
    public boolean addRecipeWithIngredients(Recipe recipe, List<Ingredient> ingredients) {
    String insertRecipeQuery = "INSERT INTO Recipes (user_id, recipe_name, category, servings, instructions, preparation_time) VALUES (?, ?, ?, ?, ?, ?)";
    String insertIngredientQuery = "INSERT INTO Ingredients (recipe_id, ingredient_name, amount, unit) VALUES (?,?, ?, ?)";

    Connection conn = null;
    try {
        conn = DatabaseConnection.getConnection();
        
        conn.setAutoCommit(false);

        
        try (PreparedStatement recipeStmt = conn.prepareStatement(insertRecipeQuery, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            recipeStmt.setInt(1, recipe.getUserId());
            recipeStmt.setString(2, recipe.getRecipeName());
            recipeStmt.setString(3, recipe.getCategory());
            recipeStmt.setInt(4, recipe.getServing());
            recipeStmt.setString(5, recipe.getInstructions());
            recipeStmt.setInt(6, recipe.getPreparationTime());

            recipeStmt.executeUpdate();

            try (ResultSet generatedKeys = recipeStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedRecipeId = generatedKeys.getInt(1);
                    
                    //storage all the ingredients for this recipe
                    try (PreparedStatement ingredientStmt = conn.prepareStatement(insertIngredientQuery)) {
                        for (Ingredient ingredient : ingredients) {
                            ingredientStmt.setInt(1, generatedRecipeId); // شناسه غذای تازه ثبت شده
                            ingredientStmt.setString(2, ingredient.getIngredientName());
                            ingredientStmt.setDouble(3, ingredient.getAmount());
                            ingredientStmt.setString(4, ingredient.getUnit());
                            ingredientStmt.executeUpdate();
                        }
                    }
                } else {
                    throw new SQLException("Creating recipe failed, no ID obtained.");
                }
            }
        }

        // if all of the code above runs successfully, we can commit the transaction
        conn.commit();
        System.out.println("Recipe and all ingredients saved successfully using Transaction! 💎");
        return true;

    } catch (SQLException e) {
        // if something goes wrong, we should roll back the transaction to maintain data integrity
        if (conn != null) {
            try {
                System.out.println("Something went wrong! Rolling back changes...");
                conn.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        }
        System.out.println("Transaction failed: " + e.getMessage());
        return false;
    } finally {
        //at the end, we should always reset the auto-commit mode and close the connection
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
public boolean deleteRecipe(int recipeId) {
    // دو تا کوئری جداگانه برای حذف مواد اولیه و حذف خود غذا
    String deleteIngredientsQuery = "DELETE FROM Ingredients WHERE recipe_id = ?";
    String deleteRecipeQuery = "DELETE FROM Recipes WHERE id = ?";

    Connection conn = null;
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        // first, delete all ingredients associated with the recipe(child records)
        try (PreparedStatement ingredientStmt = conn.prepareStatement(deleteIngredientsQuery)) {
            ingredientStmt.setInt(1, recipeId); 
            ingredientStmt.executeUpdate(); //remove all ingredients for the recipe
        }

        // delete the recipe itself(mother recipe)
        try (PreparedStatement recipeStmt = conn.prepareStatement(deleteRecipeQuery)) {
            recipeStmt.setInt(1, recipeId); 
            int rowsAffected = recipeStmt.executeUpdate();
            
            //if rowsAffected is 0, it means the recipe with the given ID was not found, so we throw an exception to trigger a rollback
            if (rowsAffected == 0) {
                throw new SQLException("Recipe not found with ID: " + recipeId);
            }
        }

        //if all operations were successful, we can commit the transaction
        conn.commit();
        System.out.println("Recipe and its ingredients deleted successfully!");
        return true;

    } catch (SQLException e) {
        // if any part of the transaction fails, we roll back to maintain data integrity
        if (conn != null) {
            try {
                System.out.println("Delete failed! Rolling back changes...");
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        }
        System.out.println("Transaction failed: " + e.getMessage());
        return false;
    } finally {
        // turn the auto-commit back on and close the connection
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
public boolean updateRecipe(Recipe recipe) {
    String updateQuery = "UPDATE Recipes SET recipe_name = ?, category = ?, servings = ?, instructions = ?, preparation_time = ? WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

        //using encapsulation to set the values for the update query
        stmt.setString(1, recipe.getRecipeName());
        stmt.setString(2, recipe.getCategory());
        stmt.setInt(3, recipe.getServing());
        stmt.setString(4, recipe.getInstructions());
        stmt.setInt(5, recipe.getPreparationTime());
        stmt.setInt(6, recipe.getId()); //getting the recipe id to know wich recipe needs to change

        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Recipe updated successfully in database!");
            return true;
        } else {
            System.out.println("No recipe found with ID: " + recipe.getId());
            return false;
        }

    } catch (SQLException e) {
        System.out.println("Database error while updating recipe: " + e.getMessage());
        return false;
    }
}
}
