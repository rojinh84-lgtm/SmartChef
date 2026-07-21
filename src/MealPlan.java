public class MealPlan {
    private int id;
    private int userId;
    private int recipeId;
    private String dayOfWeek;
    private String mealType;
    private String recipeName;
    
    public MealPlan(){
        //default (if you want to create an object without setting any values)
    }
    public MealPlan(int id, int userId, int recipeId, String dayOfWeek, String mealType) {
    this.id = id;
    this.userId = userId;
    this.recipeId = recipeId;
    this.dayOfWeek = dayOfWeek;
    this.mealType = mealType;
    }
    public int getId() {
        return id;  }
    public void setId(int id) {
        this.id = id;  }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getMealType() {
        return mealType;
    }
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;  }
}
