package com.hotelone.controllers;

import com.hotelone.services.ReservaService;
import com.hotelone.utils.SceneRender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class RegistroReservaController extends AbstractReservaController {

    public Button botaoContinuar = new Button();

    public RegistroReservaController() {
        super(new ReservaService());
    }

    @FXML
    public void botaoContinuarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        setReservaData();
        RegistroHospedeController controller = new RegistroHospedeController(reservaData, reservaService);
        new SceneRender(source, "registro-hospede-view.fxml", controller).update();
    }

    @Override
    public void pagamentoChoiceBoxHandler() {
        botaoContinuar.setDisable(false);
    }

    @Override
    protected void desabilitarBotao() {
        botaoContinuar.setDisable(true);
    }
}
