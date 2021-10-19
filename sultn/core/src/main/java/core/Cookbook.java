package core;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * A Cookbook object. A cookbook object is a collection of Recipe objects and contains methods that
 * add, delete, edit and make new Recipe-objects to be stored within the Cookbook object.
 */
public class Cookbook {

  private TreeMap<Integer, Recipe> recipeMap = new TreeMap<>();

  private int idCounter = 0; // Keeps track of next available ID.

  /**
   * Cookbook - Default constructor (no parameters).
   */
  public Cookbook() {}

  /**
   * Cookbook constructor.
   *
   * @param recipeMap - Map of Recipe objects
   */
  public Cookbook(TreeMap<Integer, Recipe> recipeMap) {
    recipeMap.forEach((key, value) -> this.recipeMap.put(key, value));
  }

  /**
   * Get the recipe map.
   *
   * @return The recipe map
   */
  public TreeMap<Integer, Recipe> getRecipeMap() {
    TreeMap<Integer, Recipe> returnMap = new TreeMap<>();
    returnMap.putAll(this.recipeMap);
    return returnMap;
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
   * Makes a new Recipe and add it to Cookbook.
   *
   * @param name - Name of the Recipe
   * @param instructions - List of instructions
   * @param ingredients - List of Ingredient-objects
   */
  public int makeNewRecipe(String name, List<String> instructions, List<Ingredient> ingredients) {
    recipeMap.put(idCounter, new Recipe(name, idCounter, ingredients, instructions));

    // Update next available ID. Should be incrementally if added in UI.
    return idCounter++;
  }

  /**
   * Delete a Recipe from Cookbook.
   *
   * @param id - ID of Recipe-object to be deleted
   */
  public void deleteRecipe(int id) {
    if (recipeMap.containsKey(id)) {
      recipeMap.remove(id);
    } else {
      throw new IllegalArgumentException(id + " not found. Invalid id.");
    }

    // Frees up ID if deleted recipe had highest ID number.
    idCounter = recipeMap.lastKey() + 1;
  }

  /**
   * Manually adds a Recipe to the recipeMap. Setting Recipe IDs manually is dangerous and should
   * only be done when initializing from file.
   *
   * @param recipe - Recipe-object to be added.
   * @throws IllegalArgumentException If a recipe with the same ID already exists.
   */
  public void addRecipe(Recipe recipe) throws IllegalArgumentException {
    int id = recipe.getId();

    // Check if recipe with this ID exists.
    if (recipeMap.containsKey(id)) {
      throw new IllegalArgumentException("Cookbook already contains a recipe with ID: " + id + ".");
    }

    // Add recipe to cookbook.
    recipeMap.put(id, recipe);

    // Update next available ID.
    idCounter = id + 1;
  }

  // denne burde kunne kalle på andre funksjoner slik at man kan endre på
  // beskrivelsen og ingredienser.
  // public Recipe editRecipe(Recipe recipe) {
  // recipe.edit(); //gjøres i recipe
  // return recipe;
  // }
}
