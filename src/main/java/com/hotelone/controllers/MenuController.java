package com.hotelone.controllers;

import com.hotelone.utils.SceneRender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuController {

    @FXML
    public Button botaoNovaReserva;

    @FXML
    public Button botaoBuscar;

    @FXML
    public Button botaoSair;

    @FXML
    public void botaoNovaReservaHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "registro-reserva-view.fxml").update();
    }

    @FXML
    public void botaoBuscarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "registros-view.fxml").update();
    }

    @FXML
    public void botaoSairHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "login-form-view.fxml").update();
    }
}
