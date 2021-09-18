package core;

/*
Class represents one recipe containing name of recipe, id for CookBook class' hash map,
array of ingredients objects and array of string instructions.
*/

public class Recipe {
    private String name;
    private int id;
    private Ingredient[] ingredients;
    private String[] instructions;


    Recipe(String name, int id, Ingredient[] ingredients, String[] instructions) {
        validateString(name);
        validateInt(id);
        validateIngArray(ingredients);
        validateStrArray(instructions);

        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public String[] getInstructions() {
        return instructions;
    }

    // Setters
    public void setName(String newName) {
        validateString(newName);
        this.name = newName;
    }

    public void setId(int newId) {
        validateInt(newId);
        this.id = newId;
    }
    
    public void setIngredients(Ingredient[] newIngredients) {
        validateIngArray(newIngredients);
        this.ingredients = newIngredients;
    }

    public void setInstructions(String[] newInstructions) {
        validateStrArray(newInstructions);
        this.instructions = newInstructions;
    }

    // Validation
    private void validateString(String toValidate) {
        if (toValidate.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
    }
    
    private void validateInt(int toValidate) {
        if (toValidate < 0) throw new IllegalArgumentException("ID cannot be negative.");
    }

    private void validateIngArray(Ingredient[] toValidate) {
        if (toValidate.length == 0) throw new IllegalArgumentException("Recipe must contain at least one ingredient.");
    }

    private void validateStrArray(String[] toValidate) {
        if (toValidate.length == 0) throw new IllegalArgumentException("Recipe must contain at least one instruction.");
    }
}
