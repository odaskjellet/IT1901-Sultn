package core;

/**
 * Ingredient class with constructor for name, amount and unit.
 * Contains "getters" and "setters" for each of the three variables.
*/

public class Ingredient {
    
    // Variables
    private String name;
    private String unit;
    private double amount;

    // Constructor
    public Ingredient(String aName, double aAmount, String aUnit) {
        name = aName;
        amount = aAmount;
        unit = aUnit;
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
    public void setIngredientName(String newName){
        name = newName;
    }

    public void setIngredientAmount(double newAmount){
        amount = newAmount;
    }

    public void setIngredientUnit(String newUnit){
        unit = newUnit;
    }
}
