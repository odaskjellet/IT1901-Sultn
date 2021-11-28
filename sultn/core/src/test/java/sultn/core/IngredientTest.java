package sultn.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ingrdient class.
 */
public class IngredientTest {

  /**
   * Tests making an ingredient with non-valid name
   */
  @Test
  @DisplayName("Testing name validation")
  public void testIngredientName() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("", 1.0, "kg");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Ingredient with a really long name", 1.0, "kg");
    });
  }

  /**
   * Tests making an ingredient with non-valid amount
   */
  @Test
  @DisplayName("Testing amount validation")
  public void testIngredientAmount() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Nothing", 0, "kg");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Too much", 10000.0, "kg");
    });
  }

  /**
   * Tests making an ingredient with non-valid unit
   */
  @Test
  @DisplayName("Testing unit validation")
  public void testIngredientUnit() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Nothing", 1, "");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Too much", 2.0, "This is a long unit name");
    });
  }

  /**
   * Tests making an ingredient with valid parameters
   */
  @Test
  @DisplayName("Testing valid ingredient")
  public void testValidIngredient() {
    Ingredient validIngredient = new Ingredient("Valid", 3, "stk");
    assertEquals("Valid", validIngredient.getIngredientName());
    assertEquals(3, validIngredient.getIngredientAmount());
    assertEquals("stk", validIngredient.getIngredientUnit());
  }
}
