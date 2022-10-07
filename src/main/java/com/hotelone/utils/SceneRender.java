package com.hotelone.utils;

import com.hotelone.IndexPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneRender {

    private final Stage stage;

    private final FXMLLoader loader;

    public SceneRender (Node source, String fxmlName, Object controller) {
        this.stage = (Stage) source.getScene().getWindow();
        this.loader = new FXMLLoader(IndexPage.class.getResource(fxmlName));
        loader.setController(controller);
    }

    public void update() throws IOException {
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }
}
