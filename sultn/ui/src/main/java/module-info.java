module sultn.ui {
    requires sultn.core;
    requires javafx.controls;
    requires javafx.fxml;
    opens ui to javafx.graphics, javafx.fxml;
}
