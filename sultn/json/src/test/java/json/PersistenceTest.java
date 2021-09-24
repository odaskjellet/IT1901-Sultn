package json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

/*
IMPORTANT:

These tests does not actually test anything, and are only used as a way to
save and load a cookbook without having to run the gui.
The tests give feedback in the form of an output file and printing to console.
*/


public class PersistenceTest {

    Cookbook cookbook = new Cookbook();
    SultnPersistence persistence = new SultnPersistence();

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

        persistence.setSaveFile("cookbook.json");
    }

    @Test
    public void testWriteCookbook() {
        try {
            persistence.saveCookBook(cookbook);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Test
    public void testReadCookbook() {
        try {
            Cookbook cb = persistence.loadCookbook();

            for (Recipe r : cb.getRecipes()) {
                System.out.println("name: " + r.getName());
                System.out.println("id: " + r.getId());
                
                System.out.println("ingredients: ");
                for (Ingredient i : r.getIngredients()) {
                    System.out.println(
                        i.getIngredientName() + " " +
                        i.getIngredientAmount() + " " +
                        i.getIngredientUnit()
                        );
                }

                for (String i : r.getInstructions()) {
                    System.out.println(i);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
