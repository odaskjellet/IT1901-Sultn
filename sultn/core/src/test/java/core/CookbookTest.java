package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for a cookbook object.
 */
public class CookbookTest {

  private Cookbook cookbook;
  private TreeMap<Integer, Recipe> testMap = new TreeMap<>();

  private Recipe pizza;
  private Recipe carbonara;
  private Recipe cake;

  private Ingredient mel = new Ingredient("mel", 1, "kg");
  private Ingredient egg = new Ingredient("egg", 2, "ost");
  private Ingredient ost = new Ingredient("ost", 5, "stk");
  private Ingredient tomatsaus = new Ingredient("tomatsaus", 1, "glass");
  private Ingredient melk = new Ingredient("melk", 2, "l");

  @BeforeEach
  public void setUp() {
    List<String> cakeInstructions = new ArrayList<String>(Arrays.asList("1.Bak en kake"));
    List<Ingredient> cakeIngredients = new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));

    List<String> pizzaInstructions =
        new ArrayList<String>(Arrays.asList("1. Lag deig", "2. Ha på saus og topping", "3. Stek"));
    List<Ingredient> pizzaIngredients =
        new ArrayList<Ingredient>(Arrays.asList(mel, ost, tomatsaus));

    List<Ingredient> carbonaraIngredients = new ArrayList<Ingredient>(Arrays.asList(egg, ost));
    List<String> carbonaraInstructions = new ArrayList<String>(
        Arrays.asList("1. Bland alle ingrediensene", "2.Kok spaghetti", "3. Server"));

    carbonara = new Recipe("Carbonara", 0, carbonaraIngredients, carbonaraInstructions);
    pizza = new Recipe("Pizza", 1, pizzaIngredients, pizzaInstructions);
    cake = new Recipe("Kake", 10, cakeIngredients, cakeInstructions);

    cookbook = new Cookbook(testMap);
    cookbook.addRecipe(carbonara);
    cookbook.addRecipe(pizza);
  }

  /**
   * Test for makeNewRecipe() method.
   */
  @Test
  @DisplayName("Testing makeNewREcipe()")
  public void testMakeNewRecipe() {

    List<Ingredient> pannekakeIngredients =
        new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));
    List<String> pannekakeInstructions =
        new ArrayList<String>(Arrays.asList("1. Mål opp mel og melk", "2. Bland alt sammen",
            "3.La røren svelle", "4. Stek og spis!"));

    int pannekakeId =
        cookbook.makeNewRecipe("Pannekake", pannekakeInstructions, pannekakeIngredients);

    assertTrue(cookbook.getIds().contains(pannekakeId));
  }


  @Test
  @DisplayName("Test deleteRecipe()")
  public void testDeleteRecipe() {
    int idPizza = pizza.getId();
    cookbook.deleteRecipe(idPizza);
    assertFalse(cookbook.getRecipeMap().containsKey(pizza.getId()),
        "The cookbook should not contain pizza after deleting the recipe!");

  }

  @Test
  @DisplayName("Testing deleteRecipe() exception")
  public void testDeleteThrow() {
    int id = -1;
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> cookbook.deleteRecipe(id));
    assertEquals(id + " not found. Invalid id.", exception.getMessage());
  }


  @Test
  @DisplayName("Testing addRecipe()")
  public void testAddRecipe() {
    cookbook.addRecipe(cake);
    assertTrue(cookbook.getRecipeMap().containsKey(cake.getId()),
        "The cookbook coes not contain the added recipe cake.");
  }

  @Test
  @DisplayName("Testing addRecipe() throws")
  public void testAddRecipeThrows() {
    cookbook.addRecipe(cake);
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(cake));
  }
}
