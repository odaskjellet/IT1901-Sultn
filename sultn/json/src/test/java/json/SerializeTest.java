package json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class SerializeTest {

    Cookbook cookbook = new Cookbook();
    SultnPersistence persistance = new SultnPersistence();

    @BeforeEach
    public void setUp() {
        Ingredient ing = new Ingredient("Eggs", 2.0, "pcs");
        List<Ingredient> ings = new ArrayList<Ingredient>(Arrays.asList(ing));
        List<String> instr = new ArrayList<String>(Arrays.asList( 
            "Fry guanciale in a pan.",
            "Cook pasta.",
            "Make slurry of eggs, parmigiano, and pepper.",
            "Add al dente pasta to frying pan along with slurry",
            "Stir constantly",
            "Add salt to taste"));
        
        Recipe recipe = new Recipe("Carbonara", 123, ings, instr);
        cookbook.addRecipe(recipe);

        persistance.setSaveFile("cookbook.json");
    }

    @Test
    public void testWriteCookbook() {
        try {
            persistance.saveCookBook(cookbook);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
