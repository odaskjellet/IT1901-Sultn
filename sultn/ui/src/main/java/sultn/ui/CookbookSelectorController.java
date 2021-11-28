package sultn.ui;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class for selecting which Cookbook the program will use.
 */
public class CookbookSelectorController {
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TextField cookbookText;
  @FXML
  private Button loadCookbook;
  @FXML
  private Button showLoadButton;
  @FXML
  private Button showCreateButton;
  @FXML
  private Button returnBtn;

  private boolean create;

  protected RestAccess restAccess;

  /**
   * Constructor that sets RestAccess.
   */
  public CookbookSelectorController() {
    this.restAccess = new RestAccess();
  }

  /**
   * Removes the buttons for selecting between loading or creating a cookbook and shows the
   * TextField and button for continuing.
   */
  public void toggleButtons() {
    showCreateButton.setVisible(false);
    showLoadButton.setVisible(false);
    returnBtn.setVisible(true);
    cookbookText.setVisible(true);
    loadCookbook.setVisible(true);
  }

  /**
   * Returns the menu for choosing between Load or Create New cookbook.
   *
   * @param event - the event from clicking the return button
   */
  public void returnToSelect(ActionEvent event) {
    showCreateButton.setVisible(true);
    showLoadButton.setVisible(true);
    returnBtn.setVisible(false);
    cookbookText.setVisible(false);
    loadCookbook.setVisible(false);
  }

  /**
   * Sets the controller to "Load" mode, and changes the UI into the next state.
   */
  public void showLoad() {
    this.create = false;
    toggleButtons();
  }

  /**
   * Sets the controller to "Create new" mode, and changes the UI into the next state.
   */
  public void showCreate() {
    this.create = true;
    toggleButtons();
  }


  /**
   * Checks if the Cookbook file already exists and handles the result depending on if the user
   * wants to create a new cookbook or load an existing one.
   *
   * @param event - the event from clicking the button
   * @throws IOException - If FXMLLoader cannot load the provided FXML.
   */
  public void loadCookbook(ActionEvent event) throws IOException {
    String cookbookName = cookbookText.getText().toLowerCase();
    try {
      validateCookbook(cookbookName);
      List<String> res = restAccess.getAllCookbookNames();
      if (create) {
        if (res.contains(cookbookName)) {
          new AlertBox("Cookbook with this name already exists. Choose a different name.",
              anchorPane);
          return;
        }
        new AlertBox("New cookbook created with name: \"" + cookbookName
            + "\". This wil be your login, so you need to remember it!", anchorPane);
      } else {
        if (!res.contains(cookbookName)) {
          new AlertBox("No cookbook with this name exists.", anchorPane);
          return;
        }
      }

      FXMLLoader loader = new FXMLLoader(getClass().getResource("Sultn.fxml"));
      Parent parent = loader.load();
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      SultnController controller = loader.getController();
      controller.setCookbookName(cookbookName);
      controller.initData(null, stage.getTitle());

      stage.setScene(new Scene(parent));
    } catch (Exception e) {
      new AlertBox(e.getMessage(), anchorPane);
    }
  }

  private void validateCookbook(String cookbookName) throws IllegalArgumentException {
    if (cookbookName.length() >= 24) {
      throw new IllegalArgumentException("Cookbook name must be less than 24 characters long");
    }
    if (!cookbookName.matches("^[a-z0-9]+$") && !cookbookName.isBlank()) {
      throw new IllegalArgumentException(
          "Cookbook name can only contain letters a-z and numbers 0-9");
    }
    if (cookbookName.isBlank()) {
      throw new IllegalArgumentException("Cookbook name cannot be empty.");
    }
  }
}
