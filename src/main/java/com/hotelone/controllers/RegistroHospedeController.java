package com.hotelone.controllers;

import com.hotelone.entities.Reserva;
import com.hotelone.services.HospedeService;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.Alerta;
import com.hotelone.utils.SceneRender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistroHospedeController extends AbstractHospedeController {

    @FXML
    private TextField reservaOutput;

    @FXML
    private Button botaoSalvar = new Button();

    private final Reserva reservaData;

    private final ReservaService reservaService;

    public RegistroHospedeController(Reserva reservaData, ReservaService reservaService) {
        super(new HospedeService());
        this.reservaData = reservaData;
        this.reservaService = reservaService;
    }

    @Override
    public void initialize() {
        super.initialize();
        reservaOutput.setText(reservaData.getId().toString());
        inputSobrenome.setDisable(true);
        inputDataNascimento.setDisable(true);
        inputTelefone.setDisable(true);
        inputNacionalidade.setDisable(true);
        botaoSalvar.setDisable(true);
    }

    @Override
    public void inputNacionalidadeHandler(ActionEvent event) {
        botaoSalvar.setDisable(false);
    }

    @Override
    public void desabilitarBotao() {}

    @Override
    public void habilitarBotao() {}

    public void botaoSalvarHandler(ActionEvent event) throws IOException {
        setHospedeData();
        reservaData.setHospede(hospedeData);
        hospedeService.save(hospedeData);
        reservaService.save(reservaData);
        new Alerta("Os dados foram salvos com sucesso.").confirmacao();
        Node source = (Node) event.getSource();
        new SceneRender(source, "menu-view.fxml").update();
    }
}