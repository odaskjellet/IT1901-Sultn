package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Cookbook;
import core.Recipe;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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

    private static Stage stage;
    private static Scene scene;

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

    /**
     * HBoxCell class inhereted from HBox Makes cells in an HBox
     * 
     * @param recipeName - recipeName to label the HBox
     * @param id         - id of chosen recipe to be parsed to a RecipeController
     *                   through a button
     * @param cookbook   - cookbook-object to be parsed to a RecipeController
     * 
     */

    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();

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
     * Makes a list of Recipes in the cookbook with a button to open a new window
     * with selected recipe in
     * 
     */
    private void createRecipeList() {

        List<HBoxCell> horisontalBoxList = new ArrayList<>();
        List<Recipe> recipeList = cookbook.getRecipes();
        for (Recipe recipe : recipeList) {
            horisontalBoxList.add(new HBoxCell(recipe.getName(), recipe.getId(), this.cookbook)); // --------------------

        }

        ListView<HBoxCell> recipeView = new ListView<HBoxCell>();
        ObservableList<HBoxCell> observableList = FXCollections.observableList(horisontalBoxList);
        recipeView.setItems(observableList);

        borderPane.setCenter(recipeView);

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
    }

}
