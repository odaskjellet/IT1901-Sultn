package core;

import core.Ingredient;
//import jdk.jfr.Timestamp;
import java.util.stream.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CookbookTest {

    private Cookbook cookbook;
    private HashMap<Integer, Recipe> testMap = new HashMap<>();

    private Recipe pizza;
    private Recipe carbonara;
    private Recipe kake;

    private Ingredient mel = new Ingredient("mel", 1, "kg");
    private Ingredient egg = new Ingredient("egg", 2, "ost");
    private Ingredient ost = new Ingredient("ost", 5, "stk");
    private Ingredient tomatsaus = new Ingredient("tomatsaus", 1, "glass");
    private Ingredient melk = new Ingredient("melk", 2, "l");

   
    

    
    @BeforeEach
    public void setUp() {
        List<String> kInstructions = new ArrayList<String>(Arrays.asList("1.Bak en kake"));
        List<Ingredient> kIngredients = new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));
    
        
    

        List<Ingredient> cIngredients = new ArrayList<Ingredient>(Arrays.asList(egg, ost));
        List<Ingredient> pIngredients = new ArrayList<Ingredient>(Arrays.asList(mel, ost, tomatsaus));

        List<String> cInstructions = new ArrayList<String>(Arrays.asList("1. Bland alle ingrediensene",
                                    "2.Kok spaghetti", "3. Server"));
        List<String> pInstructions = new ArrayList<String>(Arrays.asList("1. Lag deig", 
                                    "2. Ha på saus og topping", "3. Stek")); 
                                    

        carbonara = new Recipe("Carbonara", 0, cIngredients, cInstructions);
        pizza = new Recipe("Pizza", 1, pIngredients, pInstructions);
        kake = new Recipe("Kake", 3, kIngredients, kInstructions);

        testMap.put(1, carbonara);
        testMap.put(2, pizza);
        cookbook = new Cookbook(testMap);

        cookbook.setCounter(3);
    }

    @Test
    public void testMakeNewRecipe() {
        List<Ingredient> pannekakeIngredients = new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));
        List<String> pannekakeInstructions = new ArrayList<String>(Arrays.asList("1. Mål opp mel og melk", 
                                            "2. Bland alt sammen", "3.La røren svelle", "4. Stek og spis!"));

        cookbook.makeNewRecipe("Pannekake", pannekakeInstructions, pannekakeIngredients);

        List<Recipe> recipes = cookbook.getRecipes();

        assertTrue(recipes.stream().anyMatch(p -> p.getName().equals("Pannekake")),
         "Should be true and contain the recipe 'Pannekake'");
    }

    /*@Test
    public void testDeleteRecipe() {
        cookbook.deleteRecipe(pizza);

        List<Recipe> recipes = cookbook.getRecipes();

        /*for(Recipe recipe : recipes) {
            if(recipe.getName().equals(pizza.getName())) {
                throw new IllegalArgumentException("Should be false");
            }
        }*/

        /*assertFalse(recipes.stream().anyMatch(p -> p.getName().equals(pizza.getName()), 
        "Should return false, and shouldn't contain pizza"));*/

        //assertFalse(recipes.contains(pizza));
        //assertThrows(Exception.class, cookbook.deleteRecipe(kake), "Kake is not in list, should throw IllegalArgumentException");
   // }
   // */

    @Test
    public void testAddRecipe() {

        cookbook.addRecipe(kake);
        List<Recipe> recipes = cookbook.getRecipes();

        assertTrue(recipes.stream().anyMatch(p -> p.getName().equals("Kake")),
        "Should be true and contain the recipe 'Kake'");
    }


    //@Test
    //public void testEditRecipe();
}
