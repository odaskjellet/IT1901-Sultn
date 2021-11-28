package sultn.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SultnControllerTest extends ApplicationTest {

  private SultnController sultnController;
  private static String cookbookName = "cookbookTest.json";
  private Scene scene;

  private String backBtn = "#prevSceneButton";
  private String makeNRBtn = "#makeNewRecipeButton";

  /**
   * Start method that initilazes the SultnController and scene to be tested.
   */
  @Override
  public void start(Stage stage) throws IOException {
    String title = "Sultn";

    FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnTest.fxml"));
    Parent parent = loader.load();

    this.sultnController = loader.getController();
    this.sultnController.setCookbookName(cookbookName);
    this.sultnController.initData(null, title);

    scene = new Scene(parent);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  @AfterAll
  public static void deleteFile() {
    File f = Path.of(System.getProperty("user.home"), ".sultn/cookbookTest.json").toFile();
    if (f.exists()) {
      f.delete();
    }
  }

  /**
   * Testing controller not null.
   */
  @Test
  public void testController() {
    assertNotNull(this.sultnController);
  }

  /**
   * Test for opening a recipe. Tests that when clicked on, the button switches scene to the recipe
   * scene, and compares the title of the windows to doublecheck the switching.
   */
  @Test
  public void testOpenRecipe() {
    ObservableList<Window> window = Window.getWindows();
    String prevTitle = ((Stage) lookup("#123").query().getScene().getWindow()).getTitle();
    clickOn("#123");
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)));
    assertEquals("Carbonara", ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle());
    assertNotEquals(prevTitle, ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle(),
        "Prevoius title and current title of window should not be the same!");

  }

  /**
   * Test for opening add new recipe scene. Tests that when clicked on, the button switches scene to
   * the sultnform scene, and compares the title of the windows to doublecheck the switching.
   */
  @Test
  public void testOpenMakeNewRecipe() {
    ObservableList<Window> window = Window.getWindows();
    String prevTitle = ((Stage) lookup(makeNRBtn).query().getScene().getWindow()).getTitle();
    clickOn(makeNRBtn);
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)));
    assertEquals("Add new recipe",
        ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle());
    assertNotEquals(prevTitle, ((Stage) lookup(backBtn).query().getScene().getWindow()).getTitle(),
        "Prevoius title and current title of window should not be the same!");

  }

  /**
   * Test for checking the recipe list in sultn home.
   */
  @Test
  private void testCreateRecipeList() {
    ListView<RecipeListItem> recipeView = lookup("#recipeView").query();
    String[] names = {"Carbonara", "Pizza", "Cesar Salad", "Fried fish", "Tikka Masala",
        "Entrecote", "Flatbread", "Cake"};
    for (int count = 0; count < recipeView.getItems().size(); count++) {
      assertEquals(names[count], getLabelText("#label" + count));
    }
    assertEquals(7, recipeView.getItems().size(), "Length of recipeViewList should be 7.");
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
