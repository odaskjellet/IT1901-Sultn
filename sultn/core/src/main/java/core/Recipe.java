package core;

import java.util.ArrayList;
import java.util.List;

/**
* A Recipe object
* A Recipe object represents one recipe containing name of Recipe, id for CookBook class' HashMap,
* array of ingredients objects and array of string instructions.
* 
*/
public class Recipe {
    private String name;
    private int id;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<String> instructions = new ArrayList<>();

    /**
     * Recipe constructor
     * 
     * @param name - Name of Recipe
     * @param id - ID of Recipe
     * @param ingredients - List of Ingredients
     * @param instructions - List of instructions
     */
    public Recipe(String name, int id, List<Ingredient> ingredients, List<String> instructions) {
        validateString(name);
        validateInt(id);
        validateIngArray(ingredients);
        validateStrArray(instructions);

        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    /**
     * Get the name of the Recipe
     * 
     * @return - Name of Recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Get the ID of Recipe
     * 
     * @return - ID of Recipe
     */
    public int getId() {
        return id;
    }

    /**
     * Get the Ingredient list 
     * 
     * @return - List of Ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Get the instructions list
     * 
     * @return - List of instructions
     */
    public List<String> getInstructions() {
        return instructions;
    }

    /**
     * Specific override for UI representation of a Recipe object.
     */
    @Override
    public String toString(){
        String empt = "";
        for(String str :this.getInstructions()){
            empt = empt.concat(str + "/n");
        }
        return empt;
    }

    /**
     * Set the name of the Recipe
     * 
     * @param newName - The new name for the Recipe
     */
    public void setName(String newName) {
        validateString(newName);
        this.name = newName;
    }

    /**
     * Set the ID for Recipe
     * 
     * @param newId - The new ID for Recipe
     */
    public void setId(int newId) {
        validateInt(newId);
        this.id = newId;
    }
    
    /**
     * Replace the Ingredient list
     * 
     * @param newIngredients - List of new Ingredient(s)
     */
    public void setIngredients(List<Ingredient> newIngredients) {
        validateIngArray(newIngredients);
        this.ingredients = newIngredients;
    }

    /**
     * Replace the instructions
     * 
     * @param newInstructions - List of new instructions
     */
    public void setInstructions(List<String> newInstructions) {
        validateStrArray(newInstructions);
        this.instructions = newInstructions;
    }

    /**
     * Validation of strings
     * 
     * @param toValidate - String to be validated
     */
    private void validateString(String toValidate) {
        if (toValidate.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
    }
    
    /**
     * Validation of int
     * 
     * @param toValidate - Int to be validated
     */
    private void validateInt(int toValidate) {
        if (toValidate < 0) throw new IllegalArgumentException("ID cannot be negative.");
    }

    /**
     * Validation of Ingredient list
     * 
     * @param toValidate - List of Ingredient(s) to be validated
     */
    private void validateIngArray(List<Ingredient> toValidate) {
        if (toValidate.size() == 0) throw new IllegalArgumentException("Recipe must contain at least one ingredient.");
    }

    /**
     * Validation of Array size
     * 
     * @param toValidate - List to be validated
     */
    private void validateStrArray(List<String> toValidate) {
        if (toValidate.size() == 0) throw new IllegalArgumentException("Recipe must contain at least one instruction.");
    }
}
