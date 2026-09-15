module vslugin.de {
    requires javafx.controls;
    requires javafx.fxml;


    opens vslugin.de to javafx.fxml;
    exports vslugin.de;
}