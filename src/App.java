public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting SmartChef Application...");

        
        User fakeUser = new User(0, "rozhin_h", "Rozhin", "Hasadi", "mySecretPass123");

        // ۲. ساخت یک شیء از کلاس UserDAO تا بتونیم متد ثبت‌نام رو صدا بزنیم
        UserDAO userDAO = new UserDAO();

        // ۳. فراخوانی متد ثبت‌نام و ذخیره نتیجه
        boolean isSuccess = userDAO.registerUser(fakeUser);

        // ۴. بررسی نتیجه
        if (isSuccess) {
            System.out.println("Registration test: PASSED! User saved to database. 🎉");
        } else {
            System.out.println("Registration test: FAILED! (Username might be duplicate or database error)");
        };
    }
}
