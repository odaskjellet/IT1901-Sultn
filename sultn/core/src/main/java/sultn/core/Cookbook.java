package sultn.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import sultn.core.Recipe.Category;

/**
 * A Cookbook object. A cookbook object is a collection of Recipe objects and contains methods that
 * add, delete, edit and make new Recipe-objects to be stored within the Cookbook object. It uses a
 * counter to assign recipes with an ID.
 */
public class Cookbook {

  private TreeMap<Integer, Recipe> recipeMap = new TreeMap<>();

  private int idCounter = 0;

  /**
   * Cookbook - Default constructor (no parameters).
   */
  public Cookbook() {}

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
   * Get a single Recipe-object from the cookbook.
   *
   * @param id - ID of recipe to get.
   * @return The recipe to return.
   */
  public Recipe getRecipe(int id) {
    return recipeMap.get(id);
  }

  /**
   * Makes a new Recipe and add it to Cookbook.
   *
   * @param name - Name of the Recipe
   * @param instructions - List of instructions
   * @param ingredients - List of Ingredient-objects
   * @param category - Category of Recipe
   */
  public void makeNewRecipe(String name, List<String> instructions,
      Collection<Ingredient> ingredients, Category category) {
    recipeMap.put(idCounter, new Recipe(name, idCounter, ingredients, instructions, category));
    idCounter++;
  }

  /**
   * Deletes a Recipe from Cookbook, and frees up ID if deleted recipe had highest ID number.
   *
   * @param id - ID of Recipe-object to be deleted
   */
  public void deleteRecipe(int id) {
    if (!recipeMap.containsKey(id)) {
      throw new IllegalArgumentException(id + " not found. Invalid id.");
    }
    recipeMap.remove(id);
    if (recipeMap.isEmpty()) {
      idCounter = 0;
    } else {
      idCounter = recipeMap.lastKey() + 1;
    }
  }

  /**
   * Manually adds a Recipe to the recipeMap. If there is an ID conflict, the method automatically
   * resolves it
   *
   * @param recipe - Recipe-object to be added.
   */
  public void addRecipe(Recipe recipe) {
    int id = recipe.getId();
    if (recipeMap.containsKey(id)) {
      throw new IllegalArgumentException("Cookbook already contains a recipe with ID: " + id + ".");
    }
    recipeMap.put(id, recipe);
    idCounter = id + 1;
  }


  /**
   * Replace a recipe in the recipeMap with a different recipe.
   *
   * @param recipe - The edited recipe.
   * @throws IllegalArgumentException - If there is an error with selecting the ID.
   */
  public void editRecipe(Recipe recipe) throws IllegalArgumentException {
    int id = recipe.getId();
    if (!recipeMap.containsKey(id)) {
      throw new IllegalArgumentException("Recipe not found.");
    } else {
      recipeMap.put(id, recipe);
    }
  }
}
