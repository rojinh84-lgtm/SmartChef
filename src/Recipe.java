public class Recipe {
    private int id;
    private int userId;
    private String recipeName;
    private String category;
    private int serving;
    private String instructions;
    private int preparationTime;

    public Recipe(){
        //default
    }
    public Recipe(int id, int userId, String recipeName, String category, int serving, String instructions,int preparationTime){
        this.id = id;
        this.userId = userId;
        this.recipeName = recipeName;
        this.category = category;
        this.serving = serving;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getServing() {
        return serving;
    }
    public void setServing(int serving) {
        this.serving = serving;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public int getPreparationTime(){
        return preparationTime;
    }
    public void setPreparationTime(int preparationTime){
        this.preparationTime = preparationTime;
    }
}   

