package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RecipeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnRecipeBack;

    /**
     * Switches scene back to Sultn menu
     * 
     */

    /*
     * public void switchToSultnMenu(ActionEvent event) throws IOException { Parent
     * root = FXMLLoader.load(getClass().getResource("Sultn.fxml")); stage = (Stage)
     * ((Node) event.getSource()).getScene().getWindow(); scene = new Scene(root);
     * stage.setScene(scene); stage.show(); }
     */

}
