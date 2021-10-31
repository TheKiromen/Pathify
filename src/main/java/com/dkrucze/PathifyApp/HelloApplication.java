package com.dkrucze.PathifyApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pathify!");
        stage.setScene(scene);
        //FIXME
        // Make GUI more dynamic
        // Canvas size dependent on window size
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}