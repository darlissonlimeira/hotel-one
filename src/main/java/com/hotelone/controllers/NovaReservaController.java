package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.services.ReservaService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NovaReservaController extends AbstractReservaController{

    @FXML
    public Button botaoSalvar = new Button();

    @FXML
    protected TextField outputHospede;

    private final Hospede hospedeData;

    private final String nomeHospede;

    public NovaReservaController(Hospede hospede, ReservaService reservaService) {
        super(reservaService);
        hospedeData = hospede;
        reservaData.setHospede(hospede);
        nomeHospede = String.format("%s %s", hospede.getNome(), hospede.getSobrenome());
    }

    @Override
    public void initialize() {
        super.initialize();
        outputHospede.setText(nomeHospede);
    }

    public void botaoSalvarHandler(ActionEvent event) {
        Stage stage = getStage(event);
        reservaData.setHospede(hospedeData);
        reservaData.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
        reservaService.save(reservaData);
        stage.close();
    }

    @Override
    public void pagamentoChoiceBoxHandler() {
        botaoSalvar.setDisable(false);
    }

    @Override
    protected void desabilitarBotao() {
        botaoSalvar.setDisable(true);
    }
}

