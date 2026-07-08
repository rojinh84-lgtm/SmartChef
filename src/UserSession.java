public class UserSession {
    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }
    public static void logout(){
        currentUser = null;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static int getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getId();
        }
        return -1;}
}
