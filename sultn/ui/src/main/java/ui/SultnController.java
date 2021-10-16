package ui;

import core.Cookbook;
import core.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import json.SultnPersistence;

/**
 * SultnController class.
 */
public class SultnController {

  private Cookbook cookbook;
  private static SultnPersistence persistence = new SultnPersistence();

  @FXML
  BorderPane borderPane;

  @FXML
  Pane recipePane;

  @FXML
  TextFlow ingredientField;

  @FXML
  Button btnAddRecipe;

  @FXML
  SultnFormController sultnFormController;

  @FXML
  static RecipeController recipeController;

  private Stage stage;
  private Scene scene;

  private String saveFile = "cookbook.json";

  /**
   * Initializes a Cookbook with stored Recipes from JSON.
   */
  public void initialize() {
    persistence.setSaveFile(saveFile);

    try {
      this.cookbook = persistence.loadCookbook();
    } catch (IllegalStateException e) {
      System.out.println("Savefile not set");
    } catch (IOException e) {
      try {
        persistence.saveCookBook(this.cookbook);

      } catch (IOException exception) {
        System.out.println("Error");
      }
      System.out.println("Cannot read file");
    }

    createRecipeList();
    this.cookbook.setCounter(this.cookbook.getLargestKey() + 1);
  }

  /**
   * HBoxCell class inhereted from HBox Makes cells in an HBox.
   */
  public class HBoxCell extends HBox {
    Label label = new Label();
    Button button = new Button();

    /**
     * HBoxCell constructor.
     *
     * @param recipeName - recipeName to label the HBox
     * @param id - id of chosen recipe to be parsed to a RecipeController through a button
     * @param cookbook - cookbook-object to be parsed to a RecipeController
     */
    HBoxCell(String recipeName, int id, Cookbook cookbook) {
      super();

      label.setText(recipeName);
      label.setMaxWidth(Double.MAX_VALUE);
      HBox.setHgrow(label, Priority.ALWAYS);

      button.setText("Open");
      button.setId("" + id);

      button.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Recipe.fxml"));

            RecipeController buttonRecipeController = new RecipeController();
            loader.setController(buttonRecipeController);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);

            buttonRecipeController.initData(cookbook, id);

            stage.show();

          } catch (IOException e) {
            e.printStackTrace();
          }

        }
      });

      this.getChildren().addAll(label, button);
    }
  }

  /**
   * Makes a list of Recipes in the cookbook with a button to open a new window with selected recipe
   * in.
   */
  private void createRecipeList() {

    List<HBoxCell> horisontalBoxList = new ArrayList<>();
    List<Recipe> recipeList = cookbook.getRecipes();
    for (Recipe recipe : recipeList) {
      horisontalBoxList.add(new HBoxCell(recipe.getName(), recipe.getId(), this.cookbook));
    }

    ListView<HBoxCell> recipeView = new ListView<HBoxCell>();
    ObservableList<HBoxCell> observableList = FXCollections.observableList(horisontalBoxList);
    recipeView.setItems(observableList);

    borderPane.setCenter(recipeView);

  }

  /**
   * Switches scene to add a new recipe form.
   */
  public void switchToSultnForm(ActionEvent event) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnForm.fxml"));
    SultnFormController sultnFormController =
        new SultnFormController(this.cookbook, this, saveFile);
    loader.setController(sultnFormController);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.setResizable(false);
    // sultnFormController.dataInit(cookbook);
    stage.show();
  }

  /**
   * Method for reloading the cookbook and updating the recipeview in Sultn.
   */
  public void updateView() {

    try {
      this.cookbook = persistence.loadCookbook();
      this.cookbook.setCounter(this.cookbook.getLargestKey() + 1);
    } catch (Exception e) {
      this.cookbook.setCounter(this.cookbook.getLargestKey() + 1);
      System.out.println("Could not update view. Using old coookbook");
      e.printStackTrace();
    }
    createRecipeList();
  }
}
