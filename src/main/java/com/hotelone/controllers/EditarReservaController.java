package com.hotelone.controllers;

import com.hotelone.entities.Reserva;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.CustomCurrency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditarReservaController extends AbstractReservaController {

    @FXML
    public TextField outputHospede;

    @FXML
    public Button botaoSalvar = new Button();

    private String nomeHospede;

    public EditarReservaController(Reserva reserva, ReservaService reservaService) {
        super(reservaService);
        this.reservaData = reserva;
        this.nomeHospede = String.format("%s %s", reserva.getHospede().getNome(), reserva.getHospede().getSobrenome());
    }

    @Override
    public void initialize() {
        configurePagamentoChoices();
        outputHospede.setText(nomeHospede);
        checkinInput.setValue(reservaData.getCheckin());
        checkoutInput.setValue(reservaData.getCheckout());
        valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
        pagamentoChoiceBox.getSelectionModel().select(reservaData.getFormaDePagamento().label);
        checkinInput.setDisable(true);
    }

    @FXML
    public void botaoSalvarHandler(ActionEvent event) {
        Stage stage = getStage(event);
        reservaService.update(reservaData);
        stage.close();
    }

    @Override
    public void checkinInputHandler() {}

    @Override
    public void checkoutInputHandler() {
        if(checkoutInput.getValue().isBefore(reservaData.getCheckin()) || checkoutInput.getValue().isBefore(LocalDate.now())) {
            checkoutInput.setValue(reservaData.getCheckout());
            return;
        }
        reservaData.setCheckout(checkoutInput.getValue());
        reservaData = reservaService.create(reservaData);
        valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
    }

    @Override
    public void pagamentoChoiceBoxHandler() {
        reservaData.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
    }

    @Override
    protected void desabilitarBotao() {}
}