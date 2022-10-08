package com.hotelone.controllers;

import com.hotelone.entities.Reserva;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.CustomCurrency;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditarReservaModalFormController extends ReservaModalFormController {

    private Reserva reserva;

    public EditarReservaModalFormController(Reserva reserva, ReservaService reservaService) {
        super(reservaService);
        this.reserva = reserva;
        this.nomeHospede = String.format("%s %s", reserva.getHospede().getNome(), reserva.getHospede().getSobrenome());
    }

    @Override
    public void initialize() {
        configurePagamentoChoices();
        outputHospede.setText(nomeHospede);
        checkinInput.setValue(reserva.getCheckin());
        checkoutInput.setValue(reserva.getCheckout());
        valorOutput.setText(CustomCurrency.format(reserva.getValorTotal()));
        pagamentoChoiceBox.getSelectionModel().select(reserva.getFormaDePagamento().label);
        checkinInput.setDisable(true);
    }

    @Override
    public void botaoSalvarHandler(ActionEvent event) {
        Stage stage = getStage(event);
        reservaService.update(reserva);
        stage.close();
    }

    @Override
    public void checkinInputHandler(ActionEvent event) {}

    @Override
    public void checkoutInputHandler(ActionEvent event) {
        if(checkoutInput.getValue().isBefore(reserva.getCheckin()) || checkoutInput.getValue().isBefore(LocalDate.now())) {
            checkoutInput.setValue(reserva.getCheckout());
            return;
        }
        reserva.setCheckout(checkoutInput.getValue());
        reserva = reservaService.create(reserva);
        valorOutput.setText(CustomCurrency.format(reserva.getValorTotal()));
    }

    @Override
    public void pagamentoChoiceBoxHandler(ActionEvent event) {
        reserva.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
    }
}
