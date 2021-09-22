package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
Class represents one recipe containing name of recipe, id for CookBook class' hash map,
array of ingredients objects and array of string instructions.
*/

public class Recipe {
    private String name;
    private int id;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<String> instructions = new ArrayList<>();


    Recipe(String name, int id, List<Ingredient> ingredients, List<String> instructions) {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
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
    
    public void setIngredients(List<Ingredient> newIngredients) {
        validateIngArray(newIngredients);
        this.ingredients = newIngredients;
    }

    public void setInstructions(List<String> newInstructions) {
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

    private void validateIngArray(List<Ingredient> toValidate) {
        if (toValidate.size() == 0) throw new IllegalArgumentException("Recipe must contain at least one ingredient.");
    }

    private void validateStrArray(List<String> toValidate) {
        if (toValidate.size() == 0) throw new IllegalArgumentException("Recipe must contain at least one instruction.");
    }
}
