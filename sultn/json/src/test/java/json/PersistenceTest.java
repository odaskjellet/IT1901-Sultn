package json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
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
    }

    @AfterAll
    public static void clean() {
        File f = new File(System.getProperty("user.dir"), "cookbook.json");
        f.delete();
    }

    @Test
    public void testLoadCookbook() {
        assertThrows(IllegalStateException.class, () -> persistence.loadCookbook(), "Save file path not set...");
    }

    @Test
    public void testSaveCookbook() {
        assertThrows(IllegalStateException.class, () -> persistence.saveCookBook(cookbook), "Save file path not set...");
    }

    @Test
    public void testWriteReadCookbook() {
        persistence.setSaveFile("cookbook.json");

        // Save cookbook to file.
        try {
            persistence.saveCookBook(cookbook);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    
        // Read cookbook from file.
        Cookbook cbFromFile;
        try {
            cbFromFile = persistence.loadCookbook();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            
            cbFromFile = new Cookbook();
        }

        List<Recipe> origRecipes = cookbook.getRecipes();
        List<Recipe> fileRecipes = cbFromFile.getRecipes();

        for (int i = 0; i < origRecipes.size(); ++i) {
            Recipe origRecipe = origRecipes.get(i);
            Recipe fileRecipe = fileRecipes.get(i);

            assertEquals(fileRecipe.getName(), origRecipe.getName());
            assertEquals(fileRecipe.getId(), origRecipe.getId());
            assertEquals(fileRecipe.getInstructions(), origRecipe.getInstructions());

            List<Ingredient> origIngs = origRecipe.getIngredients();
            List<Ingredient> fileIngs = fileRecipe.getIngredients();

            for (int j = 0; j < fileIngs.size(); ++j) {
                Ingredient origSingleIng = origIngs.get(j);
                Ingredient fileSingleIng = fileIngs.get(j);

                assertEquals(fileSingleIng.getIngredientName(), origSingleIng.getIngredientName());
            }
        }
    }
}
