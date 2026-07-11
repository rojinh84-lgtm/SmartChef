import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// here in this class i use ai to make a transaction that will add a recipe and all its ingredients in one go. if any part of the process fails, the entire transaction will be rolled back to maintain data integrity.
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
}
