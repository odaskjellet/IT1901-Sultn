package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sultn Application
 * 
 */
public class Sultn extends Application {

    private static Scene scene;

    /**
     * Loads Sultn.fxml as a Scene in a Stage and shows
     */
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new
        // FXMLLoader(this.getClass().getResource("Sultn.fxml"));
        // Parent parent = fxmlLoader.load();
        // stage.setScene(new Scene(parent));
        scene = new Scene(loadFXML("Sultn"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Sultn.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}