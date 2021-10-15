package ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * SultnFormController class.
 * 
 */

public class SultnFormController {

  SultnController sultnController;

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  Button btnCancel;

  /**
   * Switches scene back to Sultn menu.
   * 
   */

  public void handleCancel(ActionEvent event) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("Sultn.fxml"));
    loader.setController(sultnController);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

}
