package core;

public class Recipe {
    private static String name;
    private static int id;
    private static Ingredient[] ingredients;
    private static String[] instructions;


    Recipe(String aName, int aId, Ingredient[] aIngredients, String[] aInstructions) {
        setName(aName);
        setId(aId);
        setIngredients(aIngredients);
        setInstructions(aInstructions);
    }

    // Getters
    public static String getName() {
        return name;
    }

    public static int getId() {
        return id;
    }

    public static Ingredient[] getIngredients() {
        return ingredients;
    }

    public static String[] getInstructions() {
        return instructions;
    }

    // Setters
    public static void setName(String newName) {
        if (newName.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        name = newName;
    }

    public static void setId(int newId) {
        if (newId < 0) throw new IllegalArgumentException("ID must be larger than 0.");
        id = newId;
    }
    
    public static void setIngredients(Ingredient[] newIngredients) {
        if (newIngredients.length == 0) throw new IllegalArgumentException("Recipe must contain at least one ingredient.");
        ingredients = newIngredients;
    }

    public static void setInstructions(String[] newInstructions) {
        if (newInstructions.length == 0) throw new IllegalArgumentException("Recipe must contain at least one instruction.");
        instructions = newInstructions;
    }
}
