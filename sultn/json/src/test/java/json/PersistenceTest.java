package json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

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

                for (String instr : r.getInstructions()) {
                    System.out.println(instr);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
