public class Ingredient {
    private int id;
    private int recipeId;
    private String ingredientName;
    private double amount;
    private String unit;
    
    public Ingredient(){
        //default       
    }
    public Ingredient(int id, int recipeId, String ingredientName, double amount, String unit){
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.amount = amount;
        this.unit = unit;
    }
    public int getId() {
        return id; }
    public void setId(int id) {
        this.id = id; }
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public String getIngredientName() {
        return ingredientName;
    }
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
