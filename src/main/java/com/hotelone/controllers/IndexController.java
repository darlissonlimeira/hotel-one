package com.hotelone.controllers;

import com.hotelone.utils.AppScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.io.IOException;

public class IndexController {
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new AppScene(source, "login-form-view.fxml").update();
    }
}