package sultn.ui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.EmptyStackException;
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
import javafx.util.Pair;
import sultn.core.Ingredient;
import sultn.core.Recipe;

/**
 * EditController class.
 */
public class EditController extends AbstractFormController {
  @FXML
  AnchorPane anchorPane;
  @FXML
  private Button saveRecipeButton;

  private int currentRecipeId;
  private Recipe currentRecipe;

  /**
   * Sets recipe ID of the recipe object to be edited.
   *
   * @param id - The recipe ID to be edited.
   * @throws IOException if it fails to set the recipe id.
   */
  public void setRecipeId(int id) throws IOException {
    this.currentRecipeId = id;
  }

  @Override
  protected void setup() {
    try {
      this.currentRecipe = restAccess.getRecipe(currentRecipeId);
    } catch (Exception e) {
      new AlertBox("FATAL ERROR: Could not retrieve recipe.\n" + e.getLocalizedMessage(),
          anchorPane);
      e.printStackTrace();
    }

    comboBoxInitialize();
    iconBoxInitialize();

    try {
      recipeIconInitialize();
    } catch (IOException e) {
      // Icon box initialization is not critical.
      e.printStackTrace();
    }

    this.recipeNameField.setText(currentRecipe.getName());

    for (Ingredient i : currentRecipe.getIngredients()) {
      ingredientList.getItems()
          .add(i.getIngredientName() + " " + i.getIngredientAmount() + " " + i.getIngredientUnit());
      this.tempIngrd.add(i);
    }

    TextField instructionField;
    for (String s : currentRecipe.getInstructions()) {
      instructionField = new TextField(s);
      instructionList.getItems().add(instructionField);
      instructionField.setStyle("-fx-max-width: 360px;");
      instructionField.setPromptText("Write an instruction here");
      instructionField.setId("instruction-" + instructionList.getItems().size());
    }
  }


  /**
   * Saves changes to the recipe.
   */
  public void handleEditRecipe(ActionEvent event) {
    try {
      for (TextField text : instructionList.getItems()) {
        if (!text.getText().isEmpty()) {
          this.instructions.add(text.getText());
        }
      }
      currentRecipe.setInstructions(instructions);
      currentRecipe.setIngredients(tempIngrd);
      currentRecipe.setName(this.recipeNameField.getText());
      currentRecipe.setCategory(this.getIconCategory());

      restAccess.editRecipe(currentRecipe);

      try {
        saveRecipeButton.setDisable(true);
        new AlertBox("Changes saved!", anchorPane);
        handlePrevScene(event);

      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      new AlertBox(e.getMessage(), anchorPane);
    }
  }

  /**
   * Initializes the ComboBox with icon options.
   *
   * @throws IOException if it fails to initialixe the ComboBox.
   */
  public void recipeIconInitialize() throws IOException {
    IconPicker iconPicker = new IconPicker(currentRecipe.getCategory());
    this.categoryIconField.getSelectionModel().select(iconPicker.getIconNumber());
  }

  /**
   * Unique case of handlePrevScene which sets current recipe in the RecipeController, which will
   * always be the controller EditController returns to.
   */
  @Override
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

      RecipeController controller = loader.getController();
      controller.initData(this, title);
      controller.setCurrentRecipe(currentRecipe);

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(parent);
      stage.setScene(scene);
      stage.setTitle(title);
    } catch (Exception e) {
      new AlertBox(e.getLocalizedMessage(), anchorPane);
    }
  }
}
