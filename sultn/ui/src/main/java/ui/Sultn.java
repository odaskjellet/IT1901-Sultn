package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sultn Application.
 */
public class Sultn extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Sultn.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /*
     * @Override public void start(Stage stage) throws IOException { try { Parent
     * root = FXMLLoader.load(getClass().getResource("Sultn.fxml")); Scene scene =
     * new Scene(root); stage.setScene(scene); stage.show(); } catch (Exception e) {
     * e.printStackTrace(); } }
     */

    public static void main(String[] args) {
        launch();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}