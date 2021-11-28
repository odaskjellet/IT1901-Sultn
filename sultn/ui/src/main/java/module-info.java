module sultn.ui {
  requires sultn.core;
  requires sultn.json;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;

  opens sultn.ui to javafx.graphics, javafx.fxml;
}
