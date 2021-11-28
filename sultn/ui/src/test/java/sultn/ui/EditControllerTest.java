package sultn.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import sultn.core.Ingredient;
import sultn.core.Recipe;
import sultn.core.Recipe.Category;

public class EditControllerTest extends ApplicationTest {

  private Scene scene;
  private static String cookbookName = "cookbookEditControllerTest.json";
  private EditController editController;
  private IconPicker iconPicker;

  private String recipeNameField = "#recipeNameField";
  private String catField = "#categoryIconField";
  private String ingList = "#ingredientList";
  private String saveRecipeBtn = "#saveRecipeButton";
  private String alertMsg = "#alertMessage";
  private String alertCloseBtn = "#closeButton";
  private String alertBox = "#alertBox";
  private String prevSceneButton = "#prevSceneButton";

  private Ingredient ingredient1 = new Ingredient("Eggs", 2.0, "pcs");
  private Ingredient ingredient2 = new Ingredient("Bacon", 150.0, "g");
  private Ingredient ingredient3 = new Ingredient("Parmgiano", 50.0, "g");

  private Collection<Ingredient> ingredients =
      new ArrayList<Ingredient>(Arrays.asList(ingredient1, ingredient2, ingredient3));
  private List<String> instructions = new ArrayList<String>(Arrays.asList("Fry guanciale in a pan.",
      "Cook pasta.", "Make slurry of eggs, parmigiano, and pepper.",
      "Add al dente pasta to frying pan along with slurry", "Stir constantly",
      "Add salt to taste"));

  private Recipe recipe = new Recipe("Carbonara", 123, ingredients, instructions, Category.OTHER);

  /**
   * Start method that initilazes the EditController.
   * 
   * @throws Exception - If rest service is not available.
   */
  @Override
  public void start(Stage stage) throws Exception {
    String title = "Edit: Carbonara";

    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditRecipeTest.fxml"));
    Parent parent = loader.load();

    this.editController = loader.getController();
    this.editController.setRecipeId(recipe.getId());
    this.editController.setCookbookName(cookbookName);
    this.editController.initData(null, title);

    scene = new Scene(parent);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Resets the recipe after each test
   * 
   * @throws Exception - if the recipe can't be edited. E.g. if the cookbook cannot be found.
   */
  @BeforeEach
  public void resetRecipe() throws Exception { // Doesn't work
    editController.restAccess
        .editRecipe(new Recipe("Carbonara", 123, ingredients, instructions, Category.OTHER));
    recipe.setCategory(Category.OTHER);
  }

  /**
   * Clean up method after all tests are finished.
   */
  @AfterEach
  public void clean() {
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
    assertNotNull(this.editController);
  }

  /**
   * Test for loaded recipe fields.
   */
  @Test
  public void testEditLoad() throws Exception {
    iconPicker = new IconPicker(recipe.getCategory());

    TextField nameText = lookup(recipeNameField).query();
    assertEquals(recipe.getName(), nameText.getText(), "Recipe name is wrong");

    ComboBox<Image> editCategory = lookup(catField).queryComboBox();
    assertEquals(iconPicker.getIconNumber(), editCategory.getSelectionModel().getSelectedIndex(),
        "Recipe category is wrong");
    List<String> ingredientNames = new ArrayList<>();
    for (String i : getListView(ingList)) {
      String[] view = i.trim().split("\\s");
      ingredientNames.add(view[0]);
    }
    assertEquals(ingredient1.getIngredientName(), ingredientNames.get(0), "Ingredient 1 is wrong");
    assertEquals(ingredient2.getIngredientName(), ingredientNames.get(1), "Ingredient 2 is wrong");
    assertEquals(ingredient3.getIngredientName(), ingredientNames.get(2), "Ingredient 3 is wrong");

    ListView<TextField> instructionList = lookup("#instructionList").queryListView();
    assertEquals(recipe.getInstructions().get(0), instructionList.getItems().get(0).getText(),
        "Instruction 1 is wrong");
    assertEquals(recipe.getInstructions().get(1), instructionList.getItems().get(1).getText(),
        "Instruction 2 is wrong");
    assertEquals(recipe.getInstructions().get(2), instructionList.getItems().get(2).getText(),
        "Instruction 3 is wrong");

  }

  /**
   * Test saving the recipe with changes and return to the recipe view.
   */
  @Test
  public void testEditRecipe() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "CarbonaraEdit");
    editController.pushSceneToStack(sceneForStack);

    clickOn(recipeNameField).eraseText(9);
    clickOn(saveRecipeBtn);

    String errMsg = getLabelText(alertMsg);
    assertEquals("Name cannot be empty.", errMsg);
    clickOn(alertCloseBtn);

    clickOn(recipeNameField).write("CarbonaraEdit");
    clickOn(catField);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);

    ObservableList<Window> window = Window.getWindows();
    clickOn(saveRecipeBtn);

    errMsg = getLabelText(alertMsg);
    assertEquals("Changes saved!", errMsg);

    clickOn(alertCloseBtn);
    assertThrows(EmptyNodeQueryException.class, () -> lookup(alertBox).query());

    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)));
  }

  /**
   * Tests the other category icon.
   */
  @Test
  public void testCategoryOther() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.OTHER;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the fish category icon.
   */
  @Test
  public void testCategoryFish() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.FISH;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the chicken category icon.
   */
  @Test
  public void testCategoryChicken() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.CHICKEN;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the meat category icon.
   */
  @Test
  public void testCategoryMeat() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.MEAT;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the vegeterian category icon.
   */
  @Test
  public void testCategoryVegetarian() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.VEGETARIAN;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the bread category icon.
   */
  @Test
  public void testCategoryBread() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.BREAD;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the pizza category icon.
   */
  @Test
  public void testCategoryPizza() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.PIZZA;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests the dessert category icon.
   */
  @Test
  public void testCategoryDessert() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Recipe.fxml", "Carbonara");
    editController.pushSceneToStack(sceneForStack);

    Category category = Category.DESSERT;
    IconPicker iconPicker = new IconPicker(category);
    clickOn(catField);
    changeCategory(iconPicker.getIconNumber());
    clickOn(saveRecipeBtn);
    clickOn(alertCloseBtn);
    recipe.setCategory(category);
    assertEquals(category, recipe.getCategory());
  }

  /**
   * Tests back button when there is no previous scene
   */
  @Test
  public void testBadBack() {
    clickOn(prevSceneButton);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Previous scene unknown.", errMsg);
    clickOn(alertCloseBtn);
  }

  /* Helper functions */

  /**
   * Retrieves list of string contents from ListView corresponding to the input ID.
   *
   * @param fxIdName - ID of ListView.
   * @return Content of ListView as a list of strings.
   */
  private List<String> getListView(String fxIdName) {
    ListView<String> view = lookup(fxIdName).query();
    return view.getItems();
  }

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

  /**
   * Changes the selected category for the recipe.
   *
   * @param i - the index of the category
   */
  private void changeCategory(int i) {
    for (int c = 0; c < i; c++) {
      type(KeyCode.DOWN);
    }
    type(KeyCode.ENTER);
  }

}

