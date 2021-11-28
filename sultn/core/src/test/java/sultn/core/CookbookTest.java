package sultn.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sultn.core.Recipe.Category;

/**
 * Tests for a cookbook object.
 */
public class CookbookTest {

  private Cookbook cookbook;

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
    Collection<Ingredient> cakeIngredients =
        new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));

    List<String> pizzaInstructions =
        new ArrayList<String>(Arrays.asList("1. Lag deig", "2. Ha på saus og topping", "3. Stek"));
    Collection<Ingredient> pizzaIngredients =
        new ArrayList<Ingredient>(Arrays.asList(mel, ost, tomatsaus));

    Collection<Ingredient> carbonaraIngredients =
        new ArrayList<Ingredient>(Arrays.asList(egg, ost));
    List<String> carbonaraInstructions = new ArrayList<String>(
        Arrays.asList("1. Bland alle ingrediensene", "2.Kok spaghetti", "3. Server"));

    carbonara =
        new Recipe("Carbonara", 0, carbonaraIngredients, carbonaraInstructions, Category.OTHER);
    pizza = new Recipe("Pizza", 1, pizzaIngredients, pizzaInstructions, Category.OTHER);
    cake = new Recipe("Kake", 10, cakeIngredients, cakeInstructions, Category.OTHER);

    cookbook = new Cookbook();
    cookbook.addRecipe(carbonara);
    cookbook.addRecipe(pizza);
  }

  /**
   * Test for makeNewRecipe() method.
   */
  @Test
  @DisplayName("Testing makeNewRecipe()")
  public void testMakeNewRecipe() {

    Collection<Ingredient> pannekakeIngredients =
        new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));
    List<String> pannekakeInstructions =
        new ArrayList<String>(Arrays.asList("1. Mål opp mel og melk", "2. Bland alt sammen",
            "3.La røren svelle", "4. Stek og spis!"));

    cookbook.makeNewRecipe("Pannekake", pannekakeInstructions, pannekakeIngredients,
        Category.OTHER);

    assertTrue(cookbook.getRecipes().stream().anyMatch(p -> p.getName().equals("Pannekake")));
  }


  @Test
  @DisplayName("Test deleteRecipe()")
  public void testDeleteRecipe() {
    int idPizza = pizza.getId();
    cookbook.deleteRecipe(idPizza);
    assertFalse(cookbook.getRecipes().contains(pizza));
  }

  @Test
  @DisplayName("Test last deleteRecipe()")
  public void testLastDeleteRecipe() {
    for (Recipe r : cookbook.getRecipes()) {
      cookbook.deleteRecipe(r.getId());
    }
    assertTrue(cookbook.getRecipes().isEmpty());
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
    assertTrue(cookbook.getRecipes().contains(cake),
        "The cookbook coes not contain the added recipe cake.");
  }

  @Test
  @DisplayName("Testing addRecipe() throws")
  public void testAddRecipeThrows() {
    cookbook.addRecipe(cake);
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(cake));
  }


  @Test
  @DisplayName("Testing editRecipe()")
  public void testEditRecipe() {
    Ingredient pepperoni = new Ingredient("pepperoni", 200, "g");
    Collection<Ingredient> editedIngredients =
        new ArrayList<Ingredient>(Arrays.asList(mel, ost, tomatsaus, pepperoni));
    List<String> editedInstructions = new ArrayList<String>(
        Arrays.asList("Lag deig", "La deigen heve", "Ha på saus og topping", "Stek"));
    Recipe editedPizza =
        new Recipe("Pepperoni Pizza", 1, editedIngredients, editedInstructions, Category.PIZZA);

    cookbook.editRecipe(editedPizza);

    assertEquals(editedPizza, cookbook.getRecipe(1));
  }

  @Test
  @DisplayName("Testing editRecipe() throws")
  public void testEditRecipeThrows() {
    Ingredient pepperoni = new Ingredient("pepperoni", 200, "g");
    Collection<Ingredient> editedIngredients =
        new ArrayList<Ingredient>(Arrays.asList(mel, ost, tomatsaus, pepperoni));
    List<String> editedInstructions = new ArrayList<String>(
        Arrays.asList("Lag deig", "La deigen heve", "Ha på saus og topping", "Stek"));
    Recipe editedPizza =
        new Recipe("Pepperoni Pizza", 50, editedIngredients, editedInstructions, Category.PIZZA);

    assertThrows(IllegalArgumentException.class, () -> cookbook.editRecipe(editedPizza));
  }
}
