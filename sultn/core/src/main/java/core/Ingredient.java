package core;

/**
 * Ingredient class with constructor for name, amount and unit.
 * Contains "getters" and "setters" for each of the three variables.
 * Throws exception if strings are empty or amount is <= 0.0
*/

public class Ingredient {
    
    // Variables
    private String name;
    private String unit;
    private double amount;

    // Constructor
    public Ingredient(String name, double amount, String unit) {
        validateString(name);
        validateNumber(amount);
        validateString(unit);

        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    // Validates
    public boolean validateNumber(double number){
        if (number <= 0.0){
            throw new IllegalArgumentException("Amount must be a positive number (0 or larger).");
        }
        return true;
    }

    public boolean validateString(String string){
        if (string.isEmpty()){
            throw new IllegalArgumentException("Name has no content.");
        }
        return true;
    }

    // Gets
    public String getIngredientName(){
        return name;
    }

    public double getIngredientAmount(){
        return amount;
    }

    public String getIngredientUnit(){
        return unit;
    }

    // Sets
    public void setIngredientName(String name){
        validateString(name);
        this.name = name;
    }

    public void setIngredientAmount(double amount){
        validateNumber(amount);
        this.amount = amount;
    }

    public void setIngredientUnit(String unit){
        validateString(unit);
        this.unit = unit;
    }
}
