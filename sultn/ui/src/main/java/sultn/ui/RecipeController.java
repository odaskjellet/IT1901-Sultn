package sultn.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sultn.core.Ingredient;
import sultn.core.Recipe;


/**
 * Controller class that handles showing a single recipe. This scene can direct to the home screen
 * and the edit screen.
 */
public class RecipeController extends AbstractController {

  @FXML
  private Label recipeNameLabel;
  @FXML
  private TextArea ingredientView;
  @FXML
  private TextArea instructionView;
  @FXML
  private Button deleteRecipeButton;
  @FXML
  private AnchorPane anchorPane;

  private int recipeId;
  private String currentFxml = "Recipe.fxml";

  @Override
  protected void setup() {
    deleteRecipeButton.setVisible(true);
  }

  /**
   * Setter for recipe to be displayed.
   *
   * @param recipe - The recipe to display.
   */
  public void setCurrentRecipe(Recipe recipe) {
    recipeId = recipe.getId();
    recipeNameLabel.setText(recipe.getName());
    writeDirectionField(recipe);
    writeIngredientField(recipe);

    IconPicker iconPicker = new IconPicker(recipe.getCategory());
    ImageView imageView = new ImageView(iconPicker.getIcon());
    recipeNameLabel.setGraphic(imageView);
  }

  /**
   * Writes the ingredient field to UI.
   *
   * @param recipe - recipe to get ingredients from.
   */
  public void writeIngredientField(Recipe recipe) {
    Collection<Ingredient> ingredients = recipe.getIngredients();
    for (Ingredient ingredient : ingredients) {
      ingredientView.appendText(ingredient.getIngredientAmount() + "\t"
          + ingredient.getIngredientUnit() + " \t" + ingredient.getIngredientName() + "\n");
    }
  }

  /**
   * Method for showing the instructions in the Recipe view.
   *
   * @param recipe - Recipe-object to get instructions from
   */
  public void writeDirectionField(Recipe recipe) {
    List<String> instructions = recipe.getInstructions();
    int i = 1;
    for (String instruction : instructions) {
      instructionView.appendText(i + ": " + instruction + "\n");
      i++;
    }
  }

  /**
   * Deletes a recipe by ID from cookbook, then saves to file.
   *
   * @throws IOException is thrown if unable to delete recipe.
   * @throws EmptyStackException is thrown if stack with scenes is empty.
   * @throws IllegalStateException is thrown if uanable to delete recipe.
   */
  public void handleDeleteRecipe(ActionEvent event) {
    try {
      restAccess.deleteRecipe(recipeId);
      new AlertBox("Recipe is now deleted. Press \"Close\" to return to the main menu.",
          anchorPane);
    } catch (Exception e) {
      new AlertBox("ERROR: Unable to delete recipe.\n" + e.getLocalizedMessage(), anchorPane);
    }

    try {
      handlePrevScene(event);
    } catch (Exception e) {
      new AlertBox("ERROR: Unable to return to previous page.\n" + e.getLocalizedMessage(),
          anchorPane);
    }
  }

  /**
   * Switches scene to EditRecipe.
   *
   * @param event - Event that triggers scene change.
   */
  public void handleOpenEditRecipe(ActionEvent event) {
    try {
      String newTitle = "Editing: " + restAccess.getRecipe(recipeId).getName();
      pushSceneToStack(new Pair<String, String>(currentFxml, getTitle()));

      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditRecipe.fxml"));
      Parent parent = loader.load();

      EditController editController = loader.getController();
      editController.setRecipeId(recipeId);
      editController.initData(this, newTitle);

      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(parent);
      stage.setTitle(newTitle);
      stage.setScene(scene);

    } catch (Exception e) {
      new AlertBox("ERROR: Unable to edit recipe.\n" + e.getLocalizedMessage(), anchorPane);
      e.printStackTrace();
    }
  }
}
