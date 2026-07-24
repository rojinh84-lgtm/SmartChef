public class App {
    public static void main(String[] args) {
        // ۱. معرفی پیش‌نیازها: مشخص کردن کاربر و ابزار دیتابیس
        int currentUserId = 1; // فرض می‌کنیم آیدی کاربری شما در دیتابیس ۱ است
        ShoppingListDAO shoppingListDAO = new ShoppingListDAO();

        // ۲. اجرای سناریوی تست تیک زدن اقلام
        System.out.println("\n--- Testing Purchase Status Update ---");

        // به برنامه می‌گوییم: برای این کاربر، وضعیت خرید پنیر را به حالت "خریداری شده" (true) در بیاور
        String itemToBuy = "Mozzarella Cheese";
        boolean isUpdated = shoppingListDAO.updatePurchaseStatus(currentUserId, itemToBuy, true);

        // ۳. بررسی نتیجه
        if (isUpdated) {
            System.out.println("Test PASSED! The item was successfully checked off the list.");
        } else {
            System.out.println("Test FAILED! Could not update the item.");
        }
    }
}