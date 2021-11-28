package sultn.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CookbookSelectorControllerTest extends ApplicationTest {
  private CookbookSelectorController cookBookSelectorController;

  private String showLoadButton = "#showLoadButton";
  private String showCreateButton = "#showCreateButton";
  private String cookbookText = "#cookbookText";
  private String loadCookbook = "#loadCookbook";
  private String returnBtn = "#returnBtn";
  private String openSelectorBtn = "#openSelectorBtn";

  private String alertMsg = "#alertMessage";
  private String alertCloseBtn = "#closeButton";

  private static String cookbookName = "cookbooktest";

  private String errMsg;
  private Scene scene;

  /**
   * Start method that initilazes up the CookbookSelectorController.
   */
  @Override
  public void start(Stage stage) throws IOException {
    String title = "SULTN";

    FXMLLoader loader = new FXMLLoader(getClass().getResource("CookbookSelectorTest.fxml"));
    Parent parent = loader.load();

    this.cookBookSelectorController = loader.getController();

    scene = new Scene(parent);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Clean up method after all tests are finished.
   */
  @BeforeEach
  public void clean() {
    File cookbookDir = Path.of(System.getProperty("user.home"), ".sultn").toFile();
    cookbookDir.mkdir();
    File cookbookFile = new File(cookbookDir, cookbookName + ".json");

    if (cookbookFile.exists()) {
      cookbookFile.delete();
    }
  }

  /**
   * Tests that the controller is not null.
   */
  @Test
  public void testController() {
    assertNotNull(this.cookBookSelectorController);
  }

  @Test
  public void testCreateThenLoadCookbook() throws Exception {
    ObservableList<Window> window = Window.getWindows();
    clickOn(showCreateButton);
    clickOn(cookbookText).write(cookbookName);
    clickOn(loadCookbook);
    clickOn(alertCloseBtn);
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)),
        "Cookbook didn't get created.");
    clickOn(openSelectorBtn);


    clickOn(showLoadButton);

    clickOn(loadCookbook);

    errMsg = getLabelText(alertMsg);
    assertEquals("Cookbook name cannot be empty.", errMsg, "Empty cookbook was made.");
    clickOn(alertCloseBtn);

    clickOn(cookbookText).write("1234567890123456789012345");
    clickOn(loadCookbook);


    errMsg = getLabelText(alertMsg);
    assertEquals("Cookbook name must be less than 24 characters long", errMsg,
        "Cookbook was made with too long name");
    clickOn(alertCloseBtn);

    doubleClickOn(cookbookText).write("a + b");
    clickOn(loadCookbook);

    errMsg = getLabelText(alertMsg);
    assertEquals("Cookbook name can only contain letters a-z and numbers 0-9", errMsg,
        "Illegal character was used in cookbook.");
    clickOn(alertCloseBtn);

    clickOn(cookbookText).eraseText(5).write(cookbookName);
    clickOn(loadCookbook);
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)), "Cookbook didn't load.");
  }

  @Test
  public void testCreateCookbook() {
    ObservableList<Window> window = Window.getWindows();
    clickOn(showCreateButton);
    clickOn(cookbookText).write(cookbookName);
    clickOn(loadCookbook);
    clickOn(alertCloseBtn);
    assertFalse(window.stream().anyMatch(p -> p.getScene().equals(scene)),
        "Cookbook didn't get created");

    clickOn(openSelectorBtn);
    clickOn(showCreateButton);
    clickOn(cookbookText).write(cookbookName);
    clickOn(loadCookbook);
    errMsg = getLabelText(alertMsg);
    assertEquals("Cookbook with this name already exists. Choose a different name.", errMsg,
        "Duplicate cookbook could be created.");
  }

  @Test
  public void testReturnBtn() {
    clickOn(showCreateButton);
    clickOn(returnBtn);
    assertDoesNotThrow(() -> clickOn(showLoadButton));
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
}
