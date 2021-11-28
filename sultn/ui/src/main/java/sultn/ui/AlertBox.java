package sultn.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Alert box with customizable output message. Provides useful feedback for the user.
 */
public class AlertBox {

  @FXML
  private Button closeButton = new Button();
  @FXML
  private AnchorPane anchorPane;

  /**
   * Constructs an alert box for the user, displaying what went wrong. Disables further interaction
   * with the program until the alert is resolved.
   *
   * @param message - Message to be displayed to the user.
   */
  AlertBox(String message, AnchorPane anchorPane) {
    this.anchorPane = anchorPane;
    Stage stage = new Stage();
    stage.initStyle(StageStyle.UNDECORATED);

    // The box.
    StackPane stackPane = new StackPane();
    stackPane.setBackground(new Background(
        new BackgroundFill(Color.rgb(246, 199, 188, 1.0), new CornerRadii(0.0), new Insets(0.0))));
    stackPane.setId("alertBox");
    stackPane.setStyle("-fx-border-color: black");
    stackPane.setPadding(new Insets(10, 10, 10, 10));

    // Label of the box.
    Font labelFont = new Font("Arial", 16);
    Label label = new Label(message);
    label.setId("alertMessage");
    label.setFont(labelFont);
    label.setWrapText(true);
    label.setTranslateY(-25);
    label.setTextAlignment(TextAlignment.CENTER);

    // Confirm button.
    Font buttonFont = new Font("Arial", 14);
    closeButton.setId("closeButton");
    closeButton.setTranslateY(50);
    closeButton.setFont(buttonFont);
    closeButton.setText("Close");
    closeButton.setBackground(new Background(
        new BackgroundFill(Color.rgb(214, 176, 167), new CornerRadii(5.0), new Insets(0.0))));
    closeButton.setOnAction((event) -> {
      handleCloseAlert(event);
    });

    stackPane.getChildren().add(label);
    stackPane.getChildren().add(closeButton);
    stage.setScene(new Scene(stackPane, 250, 150));
    stage.setAlwaysOnTop(true);
    anchorPane.setDisable(true);
    stage.showAndWait();
  }

  /**
   * Closes the alert pop-up window, and reenables interaction with the program.
   *
   * @param event - Event that triggers close()
   */
  @FXML
  public void handleCloseAlert(ActionEvent event) {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    anchorPane.setDisable(false);
    stage.close();
  }
}
