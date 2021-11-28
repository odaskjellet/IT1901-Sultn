package sultn.ui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.EmptyStackException;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Abstract controller class which all other controllers in Sultn inherits from. Provides useful
 * methods which most controllers have use of.
 */
public abstract class AbstractController {
  @FXML
  private AnchorPane anchorPane;

  protected Stage stage;
  protected String title;
  protected String cookbookName;
  protected RestAccess restAccess;

  protected Stack<Pair<String, String>> prevSceneStack;
  protected String currentFxml;

  /**
   * Main data initializer for all controller classes. Sets savefile and stores prior controllers
   * which is required for handlePrevScene().
   *
   * @param prevController - Controller from where this controller was called. May be null.
   * @param newTitle - The title the new scene has. Used for tracking previous window title, and
   *        will not be used to set scene title. This should be done manually.
   */
  public final void initData(AbstractController prevController, String newTitle) {
    if (prevController != null) {
      this.restAccess = prevController.getRestAccess();
      this.cookbookName = prevController.getCookbookName();
      this.prevSceneStack = prevController.getPrevSceneStack();
    } else {
      this.restAccess = new RestAccess();
      this.prevSceneStack = new Stack<Pair<String, String>>();
    }
    this.restAccess.setCookbookName(this.cookbookName);
    this.title = newTitle == null ? "" : newTitle;

    setup();
  }

  /**
   * Replaces the default initialize method. Avoids having to use construcor and super constructor
   * for abstract class to initialize data correctly. Should be implemented by subclassed to meet
   * their needs.
   */
  protected abstract void setup();

  /**
   * Getter for this controller's rest access provider.
   *
   * @return Rest access provider.
   */
  public final RestAccess getRestAccess() {
    return restAccess;
  }

  /**
   * Update cookbook name with a new one.
   *
   * @param cookbookName - The new cookbook name.
   */
  public final void setCookbookName(String cookbookName) {
    if (cookbookName.isBlank()) {
      throw new IllegalArgumentException("Cookbook name cannot be empty.");
    }
    this.cookbookName = cookbookName;

    if (restAccess != null) {
      restAccess.setCookbookName(cookbookName);
    }
  }

  /**
   * Gets the cookbook name of the controller.
   *
   * @return Name of controller's cookbook.
   */
  public final String getCookbookName() {
    return cookbookName;
  }

  /**
   * Getter for controller title. This is the same as the window title, but is not used to set title
   * of scene. This method is primarily used to retrieve title of previous controller, which in turn
   * is used by the handlePrevScene event handler.
   *
   * @return Window title related to current controller.
   */
  public final String getTitle() {
    return title;
  }

  /**
   * Getter for the last used scene.
   *
   * @return - the scene at the top of the stack. First String in the pair is FXML file name, and
   *         the second String is window title.
   */
  public final Stack<Pair<String, String>> getPrevSceneStack() {
    return this.prevSceneStack;
  }

  /**
   * Adds a scene to the prevSceneStack.
   *
   * @param sceneInfo - The scene to be added to the stack. Should always be the scene the user is
   *        leaving. First String in the pair is FXML file name, and the second String is window
   *        title.
   */
  public final void pushSceneToStack(Pair<String, String> sceneInfo) {
    this.prevSceneStack.push(sceneInfo);
  }


  /**
   * Event handler for navigating to previous window. Sets new scene based on members initialized in
   * initData.
   *
   * @param event - The button event.
   * @throws IllegalStateException If previous FXML is not set. Cannot return to unknown location.
   * @throws IOException If FXMLLoader cannot load the provided FXML.
   */
  public void handlePrevScene(ActionEvent event)
      throws IOException, IllegalStateException, EmptyStackException {
    try {
      if (prevSceneStack.isEmpty()) {
        throw new IllegalStateException("Previous scene unknown.");
      }
      Pair<String, String> prevSceneInfo = prevSceneStack.pop();

      String title;
      if (prevSceneInfo.getValue() == null) {
        title = "SULTN";
      } else {
        title = prevSceneInfo.getValue();
      }

      FXMLLoader loader = new FXMLLoader(Paths.get(System.getProperty("user.dir"),
          "/src/main/resources/sultn/ui/", prevSceneInfo.getKey()).toUri().toURL());
      Parent parent = loader.load();

      AbstractController controller = loader.getController();
      controller.initData(this, title);

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(parent);
      stage.setScene(scene);
      stage.setTitle(title);
    } catch (Exception e) {
      new AlertBox(e.getLocalizedMessage(), anchorPane);
    }
  }
}
