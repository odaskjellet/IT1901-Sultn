package core;

import core.Ingredient;
import jdk.jfr.Timestamp;

public class CookbookTest {

    private Cookbook cookbook;
    private Map<Integer, Recipe> testMap = new HashMap<>();

    private Ingredient mel = new Ingredient("mel", 1, "kg");
    private Ingredient egg = new Ingredient("egg", 2, "ost");
    private Ingredient ost = new Ingredient("ost", 5, "stk");
    private Ingredient tomatsaus = new Ingredient("tomatsaus", 1, "glass");
    private INgredient melk = new Ingredient("melk", 2, "l");


    
    @BeforeEach
    public void setUp() {

        List<Ingredient> cIngredients = {egg, ost};
        List<Ingredient> pIngredients = {mel, ost, tomatsaus};

        List<String> cInstructions = {"1. Bland alle ingrediensene", "2.Kok spaghetti", "3. Server"};
        List<String> pInstructions = {"1. Lag deig", "2. Ha på saus og topping", "3. Stek"}; 

        Recipe carbonara = new Recipe("Carbonara", 0, cIngredients, cInstructions);
        Recipe pizza = new Recipe("Pizza", 1, pIngredients, pInstructions);

        testMap.put(1, carbonara);
        testMap.put(2, pizza);
        cookbook = new Cookbook(testMap);

        cookbook.setCounter(3);
    }

    @Test
    public void testMakeNewRecipe() {
        List<Ingredient> pannekakeIngredients = {melk, mel, egg};
        List<String> pannekakeInstructions = {"1. Mål opp mel og melk", "2. Bland alt sammen", "3.La røren svelle", "4. Stek og spis!"};

        cookbook.makeNewRecipe("Pannekake", pannekakeInstructions, pannekakeIngredients);

        List<Recipe> recipes = cookbook.getRecipes();

        assertTrue(recipes.stream().anyMatch(p -> p.getName().equals("Pannekake")),
         "Should be true and contain the recipe 'Pannekake'");
    }

    @Test
    public void testDeleteRecipe() {
        cookbook.deleteRecipe(pizza);

        List<Recipe> recipes = cookbook.getRecipes();

        assertFalse(recipes.stream().anyMatch(p -> p.getName().equals("Pannekake"), 
        "Should return false, and shouldn't contain pizza");
        
    }

    @Test
    public void testAddRecipe() {
        Liste<String> kInstructions = {"1.Bak en kake"};
        List<Ingredient> kIngredients = {melk, mel, egg};

        Recipe kake = new Recipe("Kake", 3, kIngredients, kInstructions);

        cookbook.addRecipe(kake);
        List<Recipe> recipes = cookbook.getRecipes();

        assertTrue(recipes.stream().anyMatch(p -> p.getName().equals("Kake")),
        "Should be true and contain the recipe 'Kake'");
    }


    //@Test
    //public void testEditRecipe();
}
