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


    
    @BeforeEach
    public void setUp() {

        Ingredient[] cIngredients = {egg, ost};
        Ingredient[] pIngredients = {mel, ost, tomatsaus};

        String[] cInstructions = {"1. Bland alle ingrediensene", "2.Kok spaghetti", "3. Server"};
        String[] pInstructions = {"1. Lag deig", "2. Ha på saus og topping", "3. Stek"}; 

        Recipe carbonara = new Recipe("Carbonara", 0, cIngredients, cInstructions);
        Recipe pizza = new Recipe("Pizza", 1, pIngredients, pInstructions);

        testMap.put(1, carbonara);
        testMap.put(2, pizza);
        cookbook = new Cookbook(testMap);
    }

    @Test
    public void testMakeNewRecipe();

    @Test
    public void testDeleteRecipe() {
    };

    @Test
    public void testAddRecipe();

    @Test
    public void testEditRecipe();
}
