import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ShoppingListDAO {
    
    //method to save shopping list for a user
    public boolean saveShoppingList(int userId, List<Ingredient> shoppingList) {
        String deleteOldQuery = "DELETE FROM ShoppingListItems WHERE user_id = ?";
        String insertQuery = "INSERT INTO ShoppingListItems (user_id, ingredient_name, amount, unit) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            //transaction management
            conn.setAutoCommit(false); 
            
            //delete old shopping list items for the user
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteOldQuery)) {
                deleteStmt.setInt(1, userId);
                deleteStmt.executeUpdate();
            }
            
            //save new shopping list items for the user
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                for (Ingredient ing : shoppingList) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, ing.getIngredientName());
                    insertStmt.setDouble(3, ing.getAmount());
                    insertStmt.setString(4, ing.getUnit());
                    insertStmt.addBatch(); 
                }
                insertStmt.executeBatch();
            }
            
            //commit transaction
            conn.commit();
            System.out.println("Shopping list saved to database successfully! 💾✨");
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
                }
            }
            System.out.println("Database error while saving shopping list: " + e.getMessage());
            return false;
        } finally {
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
    //
    public boolean updatePurchaseStatus(int userId, String ingredientName, boolean isPurchased) {
        String updateQuery = "UPDATE ShoppingListItems SET is_purchased = ? WHERE user_id = ? AND ingredient_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setBoolean(1, isPurchased);
            stmt.setInt(2, userId);
            stmt.setString(3, ingredientName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item '" + ingredientName + "' marked as " + (isPurchased ? "PURCHASED ✅" : "NOT PURCHASED ❌"));
                return true;
            } else {
                System.out.println("Item '" + ingredientName + "' not found in your shopping list.");
            }

        } catch (SQLException e) {
            System.out.println("Database error while updating purchase status: " + e.getMessage());
        }
        return false;
    }
}