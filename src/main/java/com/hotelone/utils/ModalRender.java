package com.hotelone.utils;

import com.hotelone.Index;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalRender {

    private final FXMLLoader fxmlLoader;

    public ModalRender (String fxmlName, Object controller) {
        this.fxmlLoader = new FXMLLoader(Index.class.getResource(fxmlName));
        this.fxmlLoader.setController(controller);
    }

    public void show () throws IOException {
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
