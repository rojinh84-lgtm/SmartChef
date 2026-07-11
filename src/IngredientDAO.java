import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class  IngredientDAO {
    public boolean addIngredient(Ingredient ingredient) {
    String insertQuery = "INSERT INTO Ingredients (recipe_id, ingredient_name, amount, unit) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
        
        stmt.setInt(1, ingredient.getRecipeId());
        stmt.setString(2, ingredient.getIngredientName());
        stmt.setDouble(3, ingredient.getAmount());
        stmt.setString(4, ingredient.getUnit());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
        
    } catch (SQLException e) {
        System.out.println("Database error while adding ingredient: " + e.getMessage());
        return false;
    }
}
public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
    List<Ingredient> ingredientList = new ArrayList<>();//Create a list to hold the ingredients for the given recipeId
    String selectQuery = "SELECT * FROM Ingredients WHERE recipe_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
        stmt.setInt(1, recipeId);
        

        ResultSet rs = stmt.executeQuery();

       
        while (rs.next()) {
            Ingredient ingredient = new Ingredient();
            
            ingredient.setId(rs.getInt("id"));
            ingredient.setRecipeId(rs.getInt("recipe_id"));
            ingredient.setIngredientName(rs.getString("ingredient_name"));
            ingredient.setAmount(rs.getDouble("amount"));
            ingredient.setUnit(rs.getString("unit"));

            //add to the list that i made at the start of the method
            ingredientList.add(ingredient);
        }

    } catch (SQLException e) {
        System.out.println("Database error while fetching ingredients: " + e.getMessage());
    }

    return ingredientList;
}

}
