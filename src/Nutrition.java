public class Nutrition {
    private int id;
    private int recipeId;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    public Nutrition() {
        // Default constructor
    }
    // Constructor with parameters
    public Nutrition(int recipeId, double calories, double protein, double carbs, double fat) {
        this.recipeId = recipeId;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }
    // Getters and Setters
    public int getId() { 
        return id; }
    public void setId(int id) { 
        this.id = id; }
            
    public int getRecipeId() { 
        return recipeId; }
    public void setRecipeId(int recipeId) { 
        this.recipeId = recipeId; }
    
    public double getCalories() { 
        return calories; }
    public void setCalories(double calories) { 
        this.calories = calories; }
    
    public double getProtein() { 
        return protein; }
    public void setProtein(double protein) { 
        this.protein = protein; }
    
    public double getCarbs() { 
        return carbs; }
    public void setCarbs(double carbs) { 
        this.carbs = carbs; }
    
    public double getFat() { 
        return fat; }
    public void setFat(double fat) { 
        this.fat = fat; }
}

