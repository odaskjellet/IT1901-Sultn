package sultn.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import sultn.core.Cookbook;
import sultn.core.Ingredient;
import sultn.core.Recipe;
import sultn.core.Recipe.Category;

public class RecipeControllerTest extends ApplicationTest {

  private String deleteBtn = "#deleteRecipeButton";
  private String editBtn = "#openEditRecipeButton";
  private String backBtn = "#prevSceneButton";
  private String lblRecipeName = "#recipeNameLabel";
  private String ingredientView = "#ingredientView";
  private String instructionView = "#instructionView";
  private String alertMsg = "#alertMessage";
  private String alertCloseBtn = "#closeButton";
  private String alertBox = "#alertBox";

  private RecipeController recipeController;
  private static String cookbookName = "cookbookTestRecipeController.json";
  private Scene scene;

  private Ingredient ingredient = new Ingredient("Eggs", 2.0, "pcs");
  private Collection<Ingredient> ingredients = new ArrayList<Ingredient>(Arrays.asList(ingredient));
  private List<String> instructions = new ArrayList<String>(Arrays.asList("Fry guanciale in a pan.",
      "Cook pasta.", "Make slurry of eggs, parmigiano, and pepper.",
      "Add al dente pasta to frying pan along with slurry", "Stir constantly",
      "Add salt to taste"));

  private Recipe recipe = new Recipe("Carbonara", 123, ingredients, instructions, Category.OTHER);

  /**
   * Start method that initilazes the RecipeController and sets up the scene to be tested.
   * 
   * @throws Exception - if rest service is not available.
   */
  @Override
  public void start(Stage stage) throws Exception {
    String title = "Carbonara";
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeTest.fxml"));
    Parent parent = loader.load();

    this.recipeController = loader.getController();
    recipeController.setCookbookName(cookbookName);
    recipeController.setCurrentRecipe(recipe);
    recipeController.initData(null, title);

    scene = new Scene(parent);
    stage.setScene(scene);
    stage.setResizable(false);

    stage.show();
  }

  /**
   * Clean up method after all tests are finished.
   */
  @AfterAll
  public static void clean() {
    File cookbookDir = Path.of(System.getProperty("user.home"), ".sultn").toFile();
    cookbookDir.mkdir();
    File cookbookFile = new File(cookbookDir, cookbookName);

    if (cookbookFile.exists()) {
      cookbookFile.delete();
    }
  }

  /**
   * Testing that controller is not null.
   */
  @Test
  public void testController() {
    assertNotNull(this.recipeController);
  }

  /**
   * Test for checking the setUp() method in RecipeController. Tests the writeDirectionField() and
   * writeIngredientField() methods used by setUp().
   */
  @Test
  public void testSetCurrentrecipe() {
    assertEquals(recipe.getName(), (String) getLabelText(lblRecipeName));

    TextArea instructionArea = lookup(instructionView).query();

    String[] instructionsArray = instructionArea.getText().split("\n");
    List<String> instructionList = new ArrayList<>();
    int i = 1;
    for (String instruction : instructionsArray) {
      String modified = instruction.replace(i + ": ", "");
      instructionList.add(modified);
      i++;
    }
    assertEquals(recipe.getInstructions(), instructionList,
        "Written instructions are not the same as actual instructions");


    TextArea ingredientArea = lookup(ingredientView).query();

    String[] ingredientArray = ingredientArea.getText().split("\n");
    List<String> ingredientList = Arrays.asList(ingredientArray);
    List<String> expectedIngredients = new ArrayList<>();

    for (Ingredient ingredient : recipe.getIngredients()) {
      String ingredientString = "" + ingredient.getIngredientAmount() + "\t"
          + ingredient.getIngredientUnit() + " \t" + ingredient.getIngredientName();
      expectedIngredients.add(ingredientString);
    }
    assertEquals(expectedIngredients, ingredientList,
        "Written ingredients are not the same as actual ingredients");

  }

  /**
   * Test for the edit button in RecipeController. Tests that when clicked on, the button switches
   * scene to the edit scene, and compares the title of the windows to doublecheck the switching.
   */
  @Test
  public void testSwitchToEditController() {
    ObservableList<Window> window = Window.getWindows();
    String prevTitle = ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle();
    clickOn(editBtn);
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)));
    assertEquals("Editing: Carbonara",
        ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle());
    assertNotEquals(prevTitle, ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle(),
        "Prevoius title and current title of window should not be the same!");
  }

  /**
   * Test for the delete button and handleDeleteRecipe() in RecipeController. Tests that when
   * clicked on that the button gets an alert message and switches scene back to the sultn
   * homescreen.
   */
  @Test
  public void testDelete() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Sultn.fxml", "SULTN");
    recipeController.pushSceneToStack(sceneForStack);
    ObservableList<Window> window = Window.getWindows();

    clickOn(deleteBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Recipe is now deleted. Press \"Close\" to return to the main menu.", errMsg);
    clickOn(alertCloseBtn);
    // Check that alert box is closed.
    assertThrows(EmptyNodeQueryException.class, () -> lookup(alertBox).query());
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)));
  }

  @Test
  public void testBadBack() {
    clickOn(backBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Previous scene unknown.", errMsg);
    clickOn(alertCloseBtn);

  }


  /* Helper functions */

  /**
   * Retrieves text from a labels based on its ID.
   *
   * @param fxIdName - The ID of the label.
   * @return String with label text.
   */
  private String getLabelText(String fxIdName) {
    Label errLabel = lookup(fxIdName).query();
    return errLabel.getText();
  }

}
