module sultn.ui {
    requires sultn.core;
    requires sultn.json;
    requires javafx.controls;
    requires javafx.fxml;
    opens ui to javafx.graphics, javafx.fxml;
}
