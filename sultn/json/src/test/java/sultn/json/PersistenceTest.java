package sultn.json;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import sultn.core.Cookbook;
import sultn.core.Ingredient;
import sultn.core.Recipe.Category;
import sultn.core.Recipe;

public class PersistenceTest {

  Cookbook cookbook = new Cookbook();
  SultnPersistence persistence = new SultnPersistence();
  static String saveFile = "cookbookTest.json";

  @BeforeEach
  public void setUp() {
    Ingredient ing = new Ingredient("Eggs", 2.0, "pcs");
    Collection<Ingredient> ings = new ArrayList<Ingredient>(Arrays.asList(ing));
    List<String> instr = new ArrayList<String>(Arrays.asList("Fry guanciale in a pan.",
        "Cook pasta.", "Make slurry of eggs, parmigiano, and pepper.",
        "Add al dente pasta to frying pan along with slurry", "Stir constantly",
        "Add salt to taste"));

    Recipe recipe = new Recipe("Carbonara", 123, ings, instr, Category.OTHER);
    cookbook.addRecipe(recipe);
  }

  @AfterAll
  public static void clean() {
    File f = new File(System.getProperty("user.dir"), saveFile);
    f.delete();
  }

  @Test
  public void testLoadCookbook() {
    assertThrows(IllegalStateException.class, () -> persistence.loadCookbook(),
        "Save file path not set...");
  }

  @Test
  public void testSaveCookbook() {
    assertThrows(IllegalStateException.class, () -> persistence.saveCookbook(cookbook),
        "Save file path not set...");
  }

  @Test
  public void testWriteReadCookbook() {
    persistence.setSaveFile(saveFile);

    // Save cookbook to file.
    try {
      persistence.saveCookbook(cookbook);
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

      List<Ingredient> origIngs = new ArrayList<>();
      origIngs.addAll(origRecipe.getIngredients());
      List<Ingredient> fileIngs = new ArrayList<>();
      fileIngs.addAll(fileRecipe.getIngredients());

      for (int j = 0; j < fileIngs.size(); ++j) {
        Ingredient origSingleIng = origIngs.get(j);
        Ingredient fileSingleIng = fileIngs.get(j);

        assertEquals(fileSingleIng.getIngredientName(), origSingleIng.getIngredientName());
      }
    }
  }
}
