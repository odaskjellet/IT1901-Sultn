package sultn.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Recipe object A Recipe object represents one recipe containing name of Recipe, id, array of
 * ingredients objects and array of string instructions.
 */
public class Recipe {
  private String name;
  private int id; // Used to distinguish between recipes with identical names, and to access correct
                  // recipe in UI and Rest client.
  private Collection<Ingredient> ingredients = new ArrayList<>();
  private List<String> instructions = new ArrayList<>();

  /**
   * enum class Category. Contains different category a recipe can be.
   */
  public enum Category {
    OTHER, FISH, MEAT, CHICKEN, VEGETARIAN, BREAD, PIZZA, DESSERT;
  }

  private Category category;

  /**
   * Recipe constructor.
   *
   * @param name - Name of Recipe.
   * @param id - ID of Recipe.
   * @param ingredients - List of Ingredients.
   * @param instructions - List of instructions.
   * @param category - Category of recipe.
   */
  public Recipe(String name, int id, Collection<Ingredient> ingredients, List<String> instructions,
      Category category) throws IllegalArgumentException {
    this.validateName(name);
    this.validateId(id);
    this.validateIngredientArray(ingredients);
    this.validateInstructionArray(instructions);

    this.name = name;
    this.id = id;
    this.ingredients.addAll(ingredients);
    this.instructions.addAll(instructions);
    this.category = category;
  }

  /**
   * Get the name of the Recipe.
   *
   * @return - Name of Recipe.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the ID of Recipe.
   *
   * @return - ID of Recipe.
   */
  public int getId() {
    return id;
  }

  /**
   * Get the Ingredient list.
   *
   * @return - List of Ingredients.
   */
  public Collection<Ingredient> getIngredients() {
    return Collections.unmodifiableCollection(this.ingredients);
  }

  /**
   * Get the instructions list.
   *
   * @return - List of instructions.
   */
  public List<String> getInstructions() {
    return Collections.unmodifiableList(this.instructions);
  }

  /**
   * Get the category of Recipe.
   *
   * @return - category of Recipe.
   */
  public Category getCategory() {
    return this.category;
  }

  /**
   * Set the name of the Recipe.
   *
   * @param newName - The new name for the Recipe.
   */
  public void setName(String newName) throws IllegalArgumentException {
    validateName(newName);
    this.name = newName;
  }

  /**
   * Set the ID for Recipe.
   *
   * @param newId - The new ID for Recipe.
   */
  public void setId(int newId) throws IllegalArgumentException {
    validateId(newId);
    this.id = newId;
  }

  /**
   * Set the category for Recipe.
   *
   * @param newCategory - The new category for Recipe.
   */
  public void setCategory(Category newCategory) {
    this.category = newCategory;
  }

  /**
   * Replace the Ingredient list.
   *
   * @param newIngredients - List of new Ingredient(s).
   */
  public void setIngredients(Collection<Ingredient> newIngredients)
      throws IllegalArgumentException {
    validateIngredientArray(newIngredients);
    this.ingredients.clear();
    this.ingredients.addAll(newIngredients);
  }

  /**
   * Replace the instructions.
   *
   * @param newInstructions - List of new instructions.
   */
  public void setInstructions(List<String> newInstructions) throws IllegalArgumentException {
    validateInstructionArray(newInstructions);
    this.instructions.clear();
    this.instructions.addAll(newInstructions);
  }

  /**
   * Validation of strings.
   *
   * @param toValidate - String to be validated.
   */
  private void validateName(String toValidate) throws IllegalArgumentException {
    if (toValidate.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
  }

  /**
   * Validation of int.
   *
   * @param toValidate - Int to be validated.
   */
  private void validateId(int toValidate) throws IllegalArgumentException {
    if (toValidate < 0) {
      throw new IllegalArgumentException("ID cannot be negative.");
    }
  }

  /**
   * Validation of Ingredient list.
   *
   * @param toValidate - List of Ingredient(s) to be validated.
   */
  private void validateIngredientArray(Collection<Ingredient> toValidate)
      throws IllegalArgumentException {
    if (toValidate.size() == 0) {
      throw new IllegalArgumentException("Recipe must contain at least one ingredient.");
    }
  }

  /**
   * Validation of Array size.
   *
   * @param toValidate - List to be validated.
   */
  private void validateInstructionArray(List<String> toValidate) throws IllegalArgumentException {
    if (toValidate.size() == 0) {
      throw new IllegalArgumentException("Recipe must contain at least one instruction.");
    }
  }
}
