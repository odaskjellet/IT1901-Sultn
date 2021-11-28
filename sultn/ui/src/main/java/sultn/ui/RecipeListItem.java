package sultn.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import sultn.core.Recipe;

/**
 * RecipeListItem class inhereted from HBox Makes cells in an HBox. Makes cells that contains a
 * label and a button in an HBox. The label and button has an id based on a recipe id. The label
 * displays the category icon for the recipe and the recipe name. The button gets an onAction() that
 * switches the scene to show a chosen recipeview.
 */
public class RecipeListItem extends HBox {
  private Button button = new Button();

  /**
   * Creates list item with title, icon image, and a button with event handler switching over to
   * correct recipe.
   *
   * @param recipe - chosen recipe to be parsed to a RecipeController through a button, used to set
   *        button id and label id.
   * @param prevController - Source controller, used to correctly navigate back on exit from recipe.
   * @param currentScene - Source FXML which uses the prevController and its window title.
   */
  RecipeListItem(Recipe recipe, AbstractController prevController,
      Pair<String, String> currentScene) {
    super();

    IconPicker iconPicker = new IconPicker(recipe.getCategory());
    ImageView imageView = new ImageView(iconPicker.getIcon());
    Label label = new Label(recipe.getName(), imageView);

    Font labelFont = new Font("Arial", 20);

    label.setMaxWidth(Double.MAX_VALUE);
    label.setId("label" + recipe.getId());
    label.setFont(labelFont);
    label.setPrefHeight(50);
    label.setBackground(new Background(
        new BackgroundFill(Color.rgb(246, 199, 188, 1.0), new CornerRadii(0.0), new Insets(0.0))));

    HBox.setHgrow(label, Priority.ALWAYS);

    Font buttonFont = new Font("Arial", 15);
    button.setFont(buttonFont);
    button.setStyle("-fx-background-color: #f6c7bc; -fx-border-color: white;");
    button.setPrefHeight(50);
    button.setPrefWidth(100);
    button.setText("Open");
    button.setId("" + recipe.getId());

    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          prevController.pushSceneToStack(currentScene);

          FXMLLoader loader = new FXMLLoader(getClass().getResource("Recipe.fxml"));
          Parent parent = loader.load();

          RecipeController buttonRecipeController = loader.getController();
          buttonRecipeController.initData(prevController, recipe.getName());
          buttonRecipeController.setCurrentRecipe(recipe);

          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          Scene scene = new Scene(parent);
          stage.setTitle(recipe.getName());
          stage.setScene(scene);

        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    });
    this.getChildren().addAll(label, button);
  }
}

