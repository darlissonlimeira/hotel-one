package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.services.HospedeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EditarHospedeController extends AbstractHospedeController{

    public Button botaoSalvar = new Button();

    public EditarHospedeController(Hospede hospede) {
        super(new HospedeService());
        hospedeData = hospede;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputNome.setText(hospedeData.getNome());
        inputSobrenome.setText(hospedeData.getSobrenome());
        inputDataNascimento.setValue(hospedeData.getDataNascimento());
        inputTelefone.setText(hospedeData.getTelefone());
        inputNacionalidade.getSelectionModel().select(
                inputNacionalidade.getItems().indexOf(hospedeData.getNacionalidade()));
    }

    @Override
    public void botaoCancelarHandler(ActionEvent event) {
        getStage(event).close();
    }

    @FXML
    public void botaoSalvarHandler(ActionEvent event) {
        Stage modal = getStage(event);
        setHospedeData();
        hospedeService.update(hospedeData);
        modal.close();
    }

    @Override
    public void inputNacionalidadeHandler(ActionEvent event) {}

    @Override
    public void desabilitarBotao() {
        botaoSalvar.setDisable(true);
    }

    @Override
    public void habilitarBotao() {
        botaoSalvar.setDisable(false);
    }
}