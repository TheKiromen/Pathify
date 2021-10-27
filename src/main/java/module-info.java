module com.dkrucze.pathify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.dkrucze.PathifyApp to javafx.fxml;
    exports com.dkrucze.PathifyApp;
}