package sultn.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sultn.core.Recipe;

/**
 * Controller for home window showing the recipe list.
 */
public class SultnController extends AbstractController {
  @FXML
  AnchorPane anchorPane;
  @FXML
  BorderPane borderPane;

  @Override
  public void setup() {
    this.currentFxml = "Sultn.fxml";
    createRecipeList();
  }

  /**
   * Creates a list of Recipes in the main view. Each list element has a button which opens a new
   * window with the selected recipe.
   */
  private void createRecipeList() {
    Pair<String, String> currentScene = new Pair<>(currentFxml, "SULTN");

    List<RecipeListItem> recipeListItem = new ArrayList<>();
    List<Recipe> recipeList;

    try {
      recipeList = restAccess.getCookbook().getRecipes();
      // Populate recipe list with contents from retrieved cookbook.
      for (Recipe recipe : recipeList) {
        recipeListItem.add(new RecipeListItem(recipe, this, currentScene));
      }

      ListView<RecipeListItem> recipeView = new ListView<RecipeListItem>();
      recipeView.setId("recipeView");
      ObservableList<RecipeListItem> observableList = FXCollections.observableList(recipeListItem);
      recipeView.setItems(observableList);
      borderPane.setCenter(recipeView);

    } catch (Exception e) {
      new AlertBox("ERROR: Unable to retrieve recipes.\n" + e.getLocalizedMessage(), anchorPane);
      e.printStackTrace();
    }
  }

  /**
   * Switches to SultnFormController, which lets the user add new recipes.
   */
  public void handleOpenSultnForm(ActionEvent event)/* Handle instead */ throws IOException {
    pushSceneToStack(new Pair<String, String>(currentFxml, title));

    String newTitle = "Add new recipe";

    FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnForm.fxml"));
    Parent parent = loader.load();

    SultnFormController sultnFormController = loader.getController();
    sultnFormController.initData(this, newTitle);

    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(parent);
    stage.setTitle(newTitle);
    stage.setScene(scene);
  }

  /**
   * Switches scene back to CookBookSelector.
   *
   * @param event - The button event.
   * @throws IOException If fxml cannot be loaded.
   */
  public void handleOpenSelector(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CookbookSelector.fxml"));
    Parent parent = loader.load();

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(parent));
    stage.setTitle("SULTN");
  }
}
