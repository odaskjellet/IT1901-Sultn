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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    SultnFormController sultnFormController;

    @FXML
    static RecipeController recipeController;

    private static Stage stage;
    private static Scene scene;
    private Parent root;

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

            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Parent root;
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Recipe.fxml"));
                        loader.setController(recipeController);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(loader.load());
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                        /*
                         * root = FXMLLoader.load(getClass().getResource("Recipe.fxml")); stage =
                         * (Stage) ((Node) event.getSource()).getScene().getWindow(); scene = new
                         * Scene(root); stage.setResizable(false); stage.setScene(scene); stage.show();
                         */

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });

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
        List<Recipe> recipeList = cookbook.getRecipes();
        for (Recipe recipe : recipeList) {
            hBoxList.add(new HBoxCell(recipe.getName(), recipe.getId()));

        }

        ListView<HBoxCell> recipeView = new ListView<HBoxCell>();
        ObservableList<HBoxCell> observableList = FXCollections.observableList(hBoxList);
        recipeView.setItems(observableList);

        bPane.setCenter(recipeView);

    }

    /**
     * Switches scene to the chosen Recipe
     * 
     */

    public void switchToRecipeScene(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Recipe.fxml"));
        loader.setController(recipeController);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        /*
         * Parent root = FXMLLoader.load(getClass().getResource("Recipe.fxml")); stage =
         * (Stage) ((Node) event.getSource()).getScene().getWindow(); scene = new
         * Scene(root); stage.setScene(scene); stage.setResizable(false); stage.show();
         */
    }

    /**
     * Switches scene to add a new recipe form
     * 
     */

    public void switchToSultnForm(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnForm.fxml"));
        loader.setController(sultnFormController);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        /*
         * Parent root = FXMLLoader.load(getClass().getResource("SultnForm.fxml"));
         * stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); scene =
         * new Scene(root); stage.setScene(scene); stage.setResizable(false);
         * stage.show();
         */
    }

}
