package com.hotelone.controllers;

import com.hotelone.entities.Reserva;
import com.hotelone.enums.FormaDePagamentoEnum;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.CustomCurrency;
import com.hotelone.utils.SceneRender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public abstract class AbstractReservaController {

    protected final ReservaService reservaService;

    protected Reserva reservaData = new Reserva();

    @FXML
    protected DatePicker checkinInput;

    @FXML
    protected DatePicker checkoutInput;

    @FXML
    protected ChoiceBox<String> pagamentoChoiceBox;

    @FXML
    protected TextField valorOutput;

    public AbstractReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public void initialize() {
        configurePagamentoChoices();
        checkoutInput.setDisable(true);
        valorOutput.setDisable(true);
        pagamentoChoiceBox.setDisable(true);
        desabilitarBotao();
    }

    public abstract void pagamentoChoiceBoxHandler();

    protected abstract void desabilitarBotao();

    public void botaoCancelarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "menu-view.fxml").update();
    }

    public void checkinInputHandler() {
        if (checkinInput.getValue() == null) {
            checkoutInput.setValue(null);
            checkoutInput.setDisable(true);
            valorOutput.setText(null);
            valorOutput.setDisable(true);
            pagamentoChoiceBox.setValue(null);
            pagamentoChoiceBox.setDisable(true);
            desabilitarBotao();
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

    public void checkoutInputHandler() {
        if (checkoutInput.getValue() == null) {
            valorOutput.setText(null);
            valorOutput.setDisable(true);
            pagamentoChoiceBox.setValue(null);
            pagamentoChoiceBox.setDisable(true);
            desabilitarBotao();
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

    protected void setReservaData() {
        reservaData.setCheckin(checkinInput.getValue());
        reservaData.setCheckout(checkoutInput.getValue());
        reservaData = reservaService.create(reservaData);
        reservaData.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
    }

    protected void configurePagamentoChoices() {
        pagamentoChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(FormaDePagamentoEnum.values()).map(pagamentoEnum -> pagamentoEnum.label).toList()
        ));
    }

    protected FormaDePagamentoEnum getFormaDePagamento(String formaDePagemnto) {
        return switch (formaDePagemnto) {
            case "Boleto" -> FormaDePagamentoEnum.BOLETO;
            case "Cartao de Credito" -> FormaDePagamentoEnum.CARTAO_DE_CREDITO;
            case "Cartao de Debito" -> FormaDePagamentoEnum.CARTAO_DE_DEBITO;
            default -> null;
        };
    }

    protected Stage getStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}
