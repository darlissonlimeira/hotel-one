package com.hotelone.utils;

import com.hotelone.Index;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppScene {

    private final String fxmlName;

    private final Stage stage;

    public AppScene (Node source, String fxmlName) {
        this.fxmlName = fxmlName;
        this.stage = (Stage) source.getScene().getWindow();
    }

    public void update() throws IOException {
        FXMLLoader loader = new FXMLLoader(Index.class.getResource(fxmlName));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }
}
