package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

/**
 * Tests for the ingrdient class.
 */
public class IngredientTest {

  /**
   * Tests making a new ingredient.
   */
  @Test
  @DisplayName("Testing new ingredient")
  public void testNewIngredient() {

    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("", 1.0, "kg");
    }, "Should throw exception for empty name");

    assertThrows(Exception.class, () -> {
      new Ingredient("Gulrot", -1.0, "kg");
    }, "Should throw exception for ingredient less than zero");

    assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Salt", 1.0, "");
    }, "Should throw exception for empty unit");
  }

}
