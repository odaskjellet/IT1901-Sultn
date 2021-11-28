package sultn.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SultnFormControllerTest extends ApplicationTest {
  @FXML
  private ListView<TextField> ingredientList;

  private SultnFormController sultnFormController;
  private static String cookbookName = "cookbookTestSultnForm.json";

  private String recipeNameField = "#recipeNameField";
  private String ingNameField = "#ingredientNameField";
  private String ingAmtField = "#ingredientAmountField";
  private String ingUnitField = "#ingredientUnitField";
  private String addIngBtn = "#addIngredient";
  private String ingList = "#ingredientList";
  private String addInstBtn = "#addInstruction";
  private String prevScnBtn = "#prevSceneButton";
  private String saveRecipeBtn = "#saveRecipeButton";
  private String alertMsg = "#alertMessage";
  private String alertCloseBtn = "#closeButton";
  private String alertBox = "#alertBox";
  private String firstInstField = "#instruction-0";
  private String secondInstField = "#instruction-1";

  private Scene scene;

  /**
   * Start method that initilazes the SultnFormController.
   */
  @Override
  public void start(Stage stage) throws IOException {
    String title = "Add a new recipe";

    FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnFormTest.fxml"));
    Parent parent = loader.load();

    this.sultnFormController = loader.getController();
    this.sultnFormController.setCookbookName(cookbookName);
    this.sultnFormController.initData(null, title);

    scene = new Scene(parent);
    stage.setTitle(title);
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
   * Testing controller that controller is not null.
   */
  @Test
  public void testController() {
    assertNotNull(this.sultnFormController);
  }


  /**
   * Checks if form accepts correctly formatted recipe.
   */
  @Test
  public void testAddNewRecipe() {

    // Create new recipe using the form.
    String testName = "Corn on the cob";
    String testIngName = "Corn";
    String testIngAmnt = "1.0";
    String testIngUnit = "pcs";
    String testInstr = "Grill corn";

    clickOn(recipeNameField).write(testName);
    clickOn(ingNameField).write(testIngName);
    clickOn(ingAmtField).write(testIngAmnt);
    clickOn(ingUnitField).write(testIngUnit);
    clickOn(addIngBtn);

    for (String i : getListView(ingList)) {
      String[] view = i.trim().split("\\s");

      assertEquals(testIngName, view[0]);
      assertEquals(testIngAmnt, view[1]);
      assertEquals(testIngUnit, view[2]);
    }
    clickOn(firstInstField).write(testInstr);
    clickOn(addInstBtn);
    clickOn(secondInstField).write(testInstr);

    TextField box1 = lookup(firstInstField).query();
    assertEquals(testInstr, box1.getText());
    TextField box2 = lookup(secondInstField).query();
    assertEquals(testInstr, box2.getText());

    clickOn(saveRecipeBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("New recipe added! \n Feel free to add more.", errMsg);

    clickOn(alertCloseBtn);
    // Check that alert box is closed.
    assertThrows(EmptyNodeQueryException.class, () -> lookup(alertBox).query());
  }

  @Test
  public void testEmptyIngredientName() {
    clickOn(ingAmtField).write("1.0");
    clickOn(ingUnitField).write("l");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Ingredient name is empty.", errMsg);
  }

  @Test
  public void testTooLongIngredientName() {
    clickOn(ingNameField).write("1234567890123456789012345"); // 25 characters.
    clickOn(ingAmtField).write("1.0");
    clickOn(ingUnitField).write("l");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("The ingredient name must be less than 24 characters!", errMsg);
  }

  @Test
  public void testEmptyIngredientAmount() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingUnitField).write("l");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Ingredient amount is empty. Must be a number. 1, 3.14, etc.", errMsg);
  }

  @Test
  public void testNonNumericIngredientAmount() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingAmtField).write("text");
    clickOn(ingUnitField).write("l");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Ingredient amount is empty. Must be a number. 1, 3.14, etc.", errMsg);
  }

  @Test
  public void testNegIngredientAmount() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingAmtField).write("-1");
    clickOn(ingUnitField).write("l");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Ingredient amount must be between 0 and 10000.", errMsg);
  }

  @Test
  public void testTooLargeIngredientAmount() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingAmtField).write("10000");
    clickOn(ingUnitField).write("l");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("Ingredient amount must be between 0 and 10000.", errMsg);
  }

  @Test
  public void testBlankIngredientUnit() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingAmtField).write("1");
    clickOn(ingUnitField).write("");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("The unit is empty. Must be 'dl', 'l', etc.", errMsg);
  }

  @Test
  public void testNullIngredientUnit() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingAmtField).write("1");
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("The unit is empty. Must be 'dl', 'l', etc.", errMsg);
  }

  @Test
  public void testTooLongIngredientUnit() {
    clickOn(ingNameField).write("Test ingredient");
    clickOn(ingAmtField).write("1");
    clickOn(ingUnitField).write("xxxxxxxxxxx"); // 11 characters.
    clickOn(addIngBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("The unit must be shorter than 11 characters!", errMsg);
  }

  @Test
  public void testEmptyInstruction() {
    clickOn(recipeNameField).write("Test");
    clickOn(ingNameField).write("testing");
    clickOn(ingAmtField).write("1.0");
    clickOn(ingUnitField).write("unit");
    clickOn(addIngBtn);
    clickOn(saveRecipeBtn);
    String errMsg = getLabelText(alertMsg);
    assertEquals("ERROR: Unable to create recipe.\nRecipe must contain at least one instruction.",
        errMsg);
  }

  @Test
  public void testHandleBack() {
    Pair<String, String> sceneForStack = new Pair<String, String>("Sultn.fxml", "SULTN");
    sultnFormController.pushSceneToStack(sceneForStack);
    ObservableList<Window> window = Window.getWindows();
    clickOn(prevScnBtn);
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)));
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
}
