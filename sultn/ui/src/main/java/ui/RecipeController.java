package ui;

import java.io.IOException;

import core.Cookbook;
import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

public class RecipeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    SultnController sultnController;

    @FXML
    Button btnRecipeBack;

    /**
     * Missing: load the cookbook - should pass it through maybe get the chosen
     * recipe
     */

    void initData(Cookbook cookbook, int id) {
        Recipe loadRecipe = cookbook.getRecipeMap().get(id);
    }

    /**
     * Switches scene back to Sultn menu
     * 
     */

    public void handRecipeBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sultn.fxml"));
        loader.setController(sultnController);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
