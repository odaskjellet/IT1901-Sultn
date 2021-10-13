package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import json.SultnPersistence;

/**
 * SultnController class
 * 
 */
public class SultnController {

    private Cookbook cookbook;
    private static SultnPersistence persistence = new SultnPersistence();

    @FXML
    BorderPane bPane;

    @FXML
    Pane recipePane;

    @FXML
    TextFlow ingredientField;

    @FXML
    TextArea rText;

    @FXML
    Button btnAddRecipe;
    @FXML
    Button finish;
    @FXML
    Button cancel;

    @FXML
    Label titleTitle;
    @FXML
    Label title;
    @FXML
    Label ingredientTitle;
    @FXML
    Label instructionsTitle;

    @FXML
    TextField titleText;
    @FXML
    TextField ingredientText;
    @FXML
    TextField instructionsText;

    /**
     * Initializes a Cookbook with stored Recipes from JSON
     * 
     */
    public void initialize() {
        persistence.setSaveFile("cookbook.json");

        try {
            cookbook = persistence.loadCookbook();
        } catch (Exception e) {
            cookbook = new Cookbook();
            System.out.println("No cookbook found. Creating a new one.");
            e.printStackTrace();
        }

        createRecipeList();
    }

    @FXML
    private void switchToRecipeForm() throws IOException {
        Sultn.setRoot("SultnForm");
    }

    @FXML
    private void switchToRecipeView() throws IOException {
        Sultn.setRoot("Recipe");
    }

    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();

        HBoxCell(String recipeName, int id) {
            super();

            label.setText(recipeName);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            button.setText("Open");
            button.setId("" + id);

            // button.setOnAction(switchToRecipeView());

            /*
             * button.setOnAction(new EventHandler<ActionEvent>() {
             * 
             * @Override public void handle(ActionEvent event) { try { FXMLLoader fxmlLoader
             * = new FXMLLoader(getClass().getResource("Recipe.fxml")); Parent root1 =
             * (Parent) fxmlLoader.load(); Stage stage = new Stage();
             * stage.setTitle("Recipe"); stage.setScene(new Scene(root1)); stage.show(); }
             * catch (Exception e) { e.printStackTrace(); } } });
             */

            this.getChildren().addAll(label, button);
        }
    }

    /**
     * Makes a list of Recipes in the cookbook with a button to open a new window
     * with selected recipe in
     * 
     */

    private void createRecipeList() {

        List<HBoxCell> hBoxList = new ArrayList<>();
        List<Recipe> recipeList = cookbook.getRecipes(); // we assume this works
        for (Recipe recipe : recipeList) {
            hBoxList.add(new HBoxCell(recipe.getName(), recipe.getId()));
        }

        ListView<HBoxCell> recipeView = new ListView<HBoxCell>();
        ObservableList<HBoxCell> observableList = FXCollections.observableList(hBoxList);
        recipeView.setItems(observableList);

        bPane.setCenter(recipeView);

    }

}
