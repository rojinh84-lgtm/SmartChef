public class App {
    public static void main(String[] args) {
        // معرفی پیش‌نیازها
        int currentUserId = 1; 
        ShoppingListDAO shoppingListDAO = new ShoppingListDAO();

        // اجرای تست حذف آیتم
        System.out.println("\n--- Testing Delete Item from Shopping List ---");

        //
        String itemToDelete = "Tomato Sauce";
        boolean isDeleted = shoppingListDAO.deleteItemFromList(currentUserId, itemToDelete);

        //checking
        if (isDeleted) {
            System.out.println("Test PASSED! The item was successfully deleted.");
        } else {
            System.out.println("Test FAILED! Could not delete the item.");
        }
    }
}