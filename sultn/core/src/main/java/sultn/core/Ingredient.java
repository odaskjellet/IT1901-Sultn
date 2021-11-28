package sultn.core;

/**
 * An Ingredient object An Ingredient object represents ingredients to be added in a Recipe object.
 * Contains methods to get or set each of the member variables and validations.
 */
public class Ingredient {

  private String name;
  private String unit;
  private double amount;

  /**
   * Ingredient constructor. Validates parameters before adding.
   *
   * @param name - The name of the ingredient
   * @param amount - The amount of said ingredient
   * @param unit - The unit (kg, lbs, tbs) of the amount
   */
  public Ingredient(String name, double amount, String unit) throws IllegalArgumentException {
    this.validateName(name);
    this.validateAmount(amount);
    this.validateUnit(unit);

    this.name = name;
    this.amount = amount;
    this.unit = unit;
  }

  /**
   * Validation of ingredient amounts. Must be between 0 and 10000.
   *
   * @param amount - Number to be validated
   */
  private void validateAmount(double amount) throws IllegalArgumentException {
    if (!(amount > 0.0 && amount < 10000)) {
      throw new IllegalArgumentException("Ingredient amount must be between 0 and 10000.");
    }
  }

  /**
   * Validation of name. Can't be empty or more than 30 characters.
   *
   * @param name - String to be validated
   */
  private void validateName(String name) throws IllegalArgumentException {
    if (name.isBlank()) {
      throw new IllegalArgumentException("Ingredient name is empty.");
    }
    if (name.length() > 24) {
      throw new IllegalArgumentException("The ingredient name must be less than 24 characters!");
    }
  }

  /**
   * Validation of ingredient units. Can't be empty or more than 10 characters.
   *
   * @param unit - Unit to be validated
   */
  private void validateUnit(String unit) throws IllegalArgumentException {
    if (unit.isBlank()) {
      throw new IllegalArgumentException("The unit is empty. Must be 'dl', 'l', etc.");
    }
    if (unit.length() > 10) {
      throw new IllegalArgumentException("The unit must be shorter than 11 characters!");
    }
  }

  /**
   * Get the name of the Ingredient.
   *
   * @return - The Ingredient name
   */
  public String getIngredientName() {
    return name;
  }

  /**
   * Get the Ingredient amount.
   *
   * @return - The Ingredient amount
   */
  public double getIngredientAmount() {
    return amount;
  }

  /**
   * Get the unit of the Ingredient.
   *
   * @return - The Ingredient unit
   */
  public String getIngredientUnit() {
    return unit;
  }
}
