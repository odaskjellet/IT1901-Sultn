package core;

/**
 * An Ingredient object
 * 
 * An Ingredient object represents ingredients to be added in a Recipe object. 
 * Contains methods to get or set each of the member variables and validations.
 *
*/
public class Ingredient {
    
    private String name;
    private String unit;
    private double amount;

    /** 
     * Ingredient constructor
     * Validates before adding.
     * 
     * @param name - The name of the ingredient
     * @param amount - The amount of said ingredient
     * @param unit - The unit (kg, lbs, tbs) of the amount
     */
    public Ingredient(String name, double amount, String unit) throws IllegalArgumentException {
        validateString(name);
        validateNumber(amount);
        validateString(unit);

        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Validation of doubles
     * 
     * @param number - Number to be validated
     */
    public void validateNumber(double number) throws IllegalArgumentException {
        if (number <= 0.0){
            throw new IllegalArgumentException("Amount must be a positive number (0 or larger).");
        }
    }

    /**
     * Validation of strings
     * 
     * @param string - String to be validated
     */
    public void validateString(String string) throws IllegalArgumentException {
        if (string.isEmpty()){
            throw new IllegalArgumentException("Name has no content.");
        }
    }

    /**
     * Get the name of the Ingredient
     * 
     * @return - The Ingredient name
     */
    public String getIngredientName(){
        return name;
    }

    /**
     * Get the Ingredient amount
     * 
     * @return - The Ingredient amount
     */
    public double getIngredientAmount(){
        return amount;
    }

    /**
     * Get the unit of the Ingredient
     * 
     * @return - The Ingredient unit
     */
    public String getIngredientUnit(){
        return unit;
    }

    /**
     * Set the Ingredient name
     * 
     * @param name - The new name of the Ingredient
     */
    public void setIngredientName(String name) throws IllegalArgumentException {
        validateString(name);
        this.name = name;
    }

    /**
     * Set the Ingredient amount
     * 
     * @param amount - The new Ingredient amount
     */
    public void setIngredientAmount(double amount) throws IllegalArgumentException {
        validateNumber(amount);
        this.amount = amount;
    }

    /**
     * Set the unit of the Ingredient
     * 
     * @param unit - The new Ingredient unit 
     */
    public void setIngredientUnit(String unit) throws IllegalArgumentException {
        validateString(unit);
        this.unit = unit;
    }
    
    /**
     * Make a string specifically to be displayed in UI
     * 
     */
    @Override
    public String toString(){
        String str = getIngredientName() + "      Antall: " + getIngredientAmount() + getIngredientUnit();
        return str;
    }
}
