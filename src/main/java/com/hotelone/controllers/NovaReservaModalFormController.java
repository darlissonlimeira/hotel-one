package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.entities.Reserva;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.CustomCurrency;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.time.LocalDate;

public class NovaReservaModalFormController extends ReservaModalFormController {

    private final Hospede hospede;

    private Reserva reservaData = new Reserva();

    public NovaReservaModalFormController(Hospede hospede, ReservaService reservaService) {
        super(reservaService);
        this.hospede = hospede;
        this.nomeHospede = String.format("%s %s", hospede.getNome(), hospede.getSobrenome());
    }

    @Override
    public void initialize() {
        configurePagamentoChoices();
        outputHospede.setText(nomeHospede);
        checkoutInput.setDisable(true);
        pagamentoChoiceBox.setDisable(true);
        valorOutput.setDisable(true);
        botaoSalvar.setDisable(true);
    }

    @Override
    public void botaoSalvarHandler(ActionEvent event) {
        Stage stage = getStage(event);
        reservaData.setHospede(this.hospede);
        reservaData.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
        reservaService.save(reservaData);
        stage.close();
    }

    @Override
    public void checkinInputHandler(ActionEvent event) {
        Stage stage = getStage(event);
        if (checkinInput.getValue() == null) {
            checkoutInput.setValue(null);
            valorOutput.setText(null);
            pagamentoChoiceBox.setValue(null);
            checkoutInput.setDisable(true);
            valorOutput.setDisable(true);
            pagamentoChoiceBox.setDisable(true);
            botaoSalvar.setDisable(true);
            return;
        }

        if (checkinInput.getValue().isBefore(LocalDate.now())) {
            checkinInput.setValue(null);
            return;
        }
        reservaData.setCheckin(checkinInput.getValue());
        if (checkoutInput.getValue() != null) {
            reservaData = reservaService.create(reservaData);
            valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
        }
        checkoutInput.setDisable(false);
    }

    @Override
    public void checkoutInputHandler(ActionEvent event) {
        Stage stage = getStage(event);
        if (checkoutInput.getValue() == null) {
            pagamentoChoiceBox.setValue(null);
            valorOutput.setText(null);
            pagamentoChoiceBox.setDisable(true);
            valorOutput.setDisable(true);
            botaoSalvar.setDisable(true);
            return;
        }

        if (checkoutInput.getValue().isBefore(checkinInput.getValue())) {
            checkoutInput.setValue(null);
            return;
        }

        reservaData.setCheckout(checkoutInput.getValue());
        reservaData = reservaService.create(reservaData);
        valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
        valorOutput.setDisable(false);
        pagamentoChoiceBox.setDisable(false);
    }

    @Override
    public void pagamentoChoiceBoxHandler(ActionEvent event) {
        botaoSalvar.setDisable(false);
    }
}
