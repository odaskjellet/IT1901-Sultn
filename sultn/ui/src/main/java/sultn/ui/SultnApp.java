package sultn.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sultn Application.
 */
public class SultnApp extends Application {

  String stageTitle = "SULTN";

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CookbookSelector.fxml"));
    Parent parent = loader.load();

    stage.setScene(new Scene(parent));
    stage.setResizable(false);
    stage.setTitle(stageTitle);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
