package sultn.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sultn.core.Recipe.Category;

/**
 * Tests for the recipe class.
 */
public class RecipeTest {

  private Recipe recipe;

  private Collection<Ingredient> testIngredients;

  private List<String> testInstructions;

  /**
   * Set up before each test.
   */
  @BeforeEach
  public void setUp() {
    Ingredient testIngredient = new Ingredient("Eggs", 2.0, "pcs");
    testIngredients = new ArrayList<Ingredient>(Arrays.asList(testIngredient));
    testInstructions = new ArrayList<String>(Arrays.asList("Fry guanciale in a pan.", "Cook pasta.",
        "Make slurry of eggs, parmigiano, and pepper.",
        "Add al dente pasta to frying pan along with slurry", "Stir constantly",
        "Add salt to taste"));

    recipe = new Recipe("Carbonara", 123, testIngredients, testInstructions, Category.OTHER);
  }

  /**
   * Test for setName() and getName().
   */
  @Test
  @DisplayName("Testing setName() and getName()")
  public void testName() {
    String newName = "Pasta carbonara";
    recipe.setName(newName);
    String checkName = recipe.getName();
    Assertions.assertEquals(newName, checkName, "The new name should be pasta carbonara.");

    String emptyName = "";
    assertThrows(IllegalArgumentException.class, () -> {
      recipe.setName(emptyName);
    }, "Should throw exception if name is empty");
  }

  /**
   * Test for id in a recipe object.
   */
  @Test
  @DisplayName("Testing id")
  public void testId() {
    assertThrows(IllegalArgumentException.class, () -> recipe.setId(-1), "ID cannot be negative.");
    recipe.setId(5);
    assertTrue(recipe.getId() == 5, "The new Id was " + recipe.getId() + ", expected 5");

  }

  /**
   * Test of setIngredients() and validation
   */
  @Test
  @DisplayName("Test for setIngredients() and validation")
  public void testSetIngredients() {
    Collection<Ingredient> emptyList = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> {
      recipe.setIngredients(emptyList);
    }, "Should throw exception if ingredientlist is empty");

    Ingredient newIngredient = new Ingredient("Test", 1, "test");
    Collection<Ingredient> newCollection = new ArrayList<>(Arrays.asList(newIngredient));
    recipe.setIngredients(newCollection);
    List<Ingredient> currentIngredients = new ArrayList<>();
    currentIngredients.addAll(recipe.getIngredients());
    List<Ingredient> newIngredients = new ArrayList<>();
    newIngredients.addAll(newCollection);
    assertTrue(currentIngredients.equals(newIngredients),
        "The new ingredientlist was " + recipe.getIngredients() + ", expected " + newCollection);
  }

  /**
   * Test of setIntructions() and validation
   */
  @Test
  @DisplayName("Test for setInstructions and validation")
  public void testSetInstructions() {
    List<String> emptyList = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> {
      recipe.setInstructions(emptyList);
    }, "Should throw exception if instructionlist is empty");

    String newInstruction = "Dette er en test";
    List<String> newList = new ArrayList<>(Arrays.asList(newInstruction));
    recipe.setInstructions(newList);
    assertTrue(recipe.getInstructions().equals(newList),
        "The new ingredientlist was " + recipe.getInstructions() + ", expected " + newList);

  }

  /**
   * Test of setCategory
   */
  @Test
  @DisplayName("Test for setCategory")
  public void testSetCategory() {
    this.recipe.setCategory(Category.PIZZA);
    assertEquals(Category.PIZZA, this.recipe.getCategory());

  }
}
