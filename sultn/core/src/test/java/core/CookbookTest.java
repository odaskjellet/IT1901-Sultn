package core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    //@Test
    //public void testMakeNewRecipe() {
    //    List<Ingredient> pannekakeIngredients = new ArrayList<Ingredient>(Arrays.asList(melk, mel, egg));
    //    List<String> pannekakeInstructions = new ArrayList<String>(Arrays.asList("1. Mål opp mel og melk", 
    //                                        "2. Bland alt sammen", "3.La røren svelle", "4. Stek og spis!"));

    //    cookbook.makeNewRecipe("Pannekake", pannekakeInstructions, pannekakeIngredients);
    //        cookbook.getRecipes();
    //        assertTrue(cookbook.getRecipeMap().containsKey(3));
            //testMap.containsKey(kake.getId()));
    //}

    //@Test
    //public void testDeleteRecipe() {
    //    System.out.println(pizza.getId());
    //    System.out.println(carbonara.getId());
    //    System.out.println(cookbook.getRecipeMap());
    //    System.out.println(cookbook.getIds());

    //    cookbook.deleteRecipe(pizza);
    //    assertFalse(testMap.containsKey(1));
    //    assertThrows(Exception.class, () -> cookbook.deleteRecipe(kake), 
    //        "Kake is not in list, should throw IllegalArgumentException");
    //}
   

    //@Test
    //public void testAddRecipe() {
    //    cookbook.addRecipe(kake);
    //    assertTrue(kake.getId() == 3);
        //assertTrue(testMap.containsKey(kake.getId()));
    //}


    //@Test
    //public void testEditRecipe();
}
