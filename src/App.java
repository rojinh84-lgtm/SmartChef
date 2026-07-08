public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting SmartChef Application...");

        UserDAO userDAO = new UserDAO();

        
        System.out.println("\n--- Testing Login ---");
        User loggedInUser = userDAO.loginUser("rozhin_h", "mySecretPass123");

        
        if (loggedInUser != null) {
            System.out.println("Login test: PASSED! User ID is: " + loggedInUser.getId());
        } else {
            System.out.println("Login test: FAILED!");
        }
    }
}
