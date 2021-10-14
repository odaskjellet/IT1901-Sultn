package ui;

import java.io.IOException;
import java.util.ResourceBundle.Control;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SultnFormController {

    SultnController sultnController;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnCancel;

    /**
     * Switches scene back to Sultn menu
     * 
     */

    /*
     * public void switchToSultnMenuFromRecipe(ActionEvent event) throws IOException
     * { Parent root = FXMLLoader.load(getClass().getResource("Sultn.fxml")); stage
     * = (Stage) ((Node) event.getSource()).getScene().getWindow(); scene = new
     * Scene(root); stage.setScene(scene); stage.show(); }
     */

    /*
     * public void setBackButtonTarget() {
     * btnCancel.setOnAction(getActionEventHandler()); }
     * 
     * 
     * public EventHandler<ActionEvent> getActionEventHandler() { return actionEvent
     * -> { Control control = (Control) actionEvent.getSource(); stage = (Stage)
     * ((Node) control.getSource()).getScene().getWindow();
     * stage.setResizable(false); stage.setScene(scene); }; }
     */

}
