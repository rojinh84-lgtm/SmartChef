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
            System.out.println("\nPlease choose an option:");
            System.out.println("1. 🍲 Recipe Management");
            System.out.println("2. 📅 Meal Planning");
            System.out.println("3. 🛒 Shopping List");
            System.out.println("4. 📊 Nutrition & Health");
            System.out.println("5. ❌ Exit");
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    System.out.println("You selected Recipe Management.🍳");
                    // i will compelete this part later for recipe management
                    break;
                case 2:
                    System.out.println("You selected Meal Planning.📅");
                    // i will compelete this part later for meal planning
                    break;
                case 3:
                    System.out.println("You selected Shopping List.🛒");
                    // i will compelete this part later for shopping list
                    break;
                case 4:
                    System.out.println("You selected Nutrition & Health.📊");
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