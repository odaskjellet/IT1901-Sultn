package core;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
* A Cookbook object.
* 
* A cookbook object is a collection of Recipe objects and contains methods that add,
* delete, edit and make new Recipe-objects to be stored within the Cookbook object.
* 
*/
public class Cookbook {
    
    private HashMap<Integer, Recipe> recipeMap = new HashMap<>();
    private int counter = 0;    //Recipe ID-counter

    /**
     * Cookbook - Default constructor (no parameters)
     */
    public Cookbook() {

    }

    /**
     * Cookbook constructor
     * 
     * @param recipeMap - A HashMap of Recipe-objects
     */
    public Cookbook(HashMap<Integer, Recipe> recipeMap) { 
        this.recipeMap = recipeMap;
    }

    /**
     * Get the counter for HashMap
     * @return The counter-value
     */
    public int getCounter() {
        return this.counter;
    }
    
    /**
     * Get the recipe HashMap
     * 
     * @return The recipe HashMap
     */
    public HashMap<Integer, Recipe> getRecipeMap() {
        return this.recipeMap;
    }

    /**
     * Set the counter value
     * 
     * @param newCounter - The new value for the counter variable.
     */
    public void setCounter(int newCounter) {
        this.counter = newCounter;
    }

    /**
     * Get an ArrayList of all IDs in Cookbook.
     * 
     * @return An ArrayList for IDs in Cookbook.
     */     
    public List<Integer> getIds() { 
        return new ArrayList<>(recipeMap.keySet());
    }

    /**
     * Gets a list of all the Recipe-objects in Cookbook.
     * 
     * @return The ArrayList of all Recipes.
     */
    public List<Recipe> getRecipes() {
        List<Recipe> allRecipes = new ArrayList<Recipe>(recipeMap.values());
        return allRecipes;
    }

    /**
     * Makes a new Recipe and add it to Cookbook. Increments counter after adding.
     * 
     * @param name - Name of the Recipe
     * @param instructions - List of instructions
     * @param ingredients - List of Ingredient-objects
     */
    public void makeNewRecipe(String name, List<String> instructions, List<Ingredient> ingredients) { 
        int id = counter;
        Recipe newRecipe = new Recipe(name, id, ingredients, instructions);
        addRecipe(newRecipe);
        counter++;
    }
    
    /**
     * Delete a Recipe from Coobkook.
     * 
     * @param Recipe - Recipe to be deleted
     */
    public void deleteRecipe(Recipe recipe) { 
        int id = recipe.getId();
        if(recipeMap.containsKey(id)) {
            recipeMap.remove(id); 
        }
        else {
            throw new IllegalArgumentException("Recipe with ID " + id + " not found. Invalid id.");
        }
    }
        
    /**
     * Add a Recipe to the recipeMap
     * 
     * @param recipe - Recipe-object to be added.
     */
    public void addRecipe(Recipe recipe) {
        int id = recipe.getId();
        recipeMap.put(id, recipe);
    }
    
    public Recipe editRecipe(int id) {  
        if(recipeMap.containsKey(id)){
            return recipeMap.get(id);
        }
        else {
            throw new IllegalArgumentException("Recipe with ID " + id + " not found. Invalid id.");
        }
    }

}

