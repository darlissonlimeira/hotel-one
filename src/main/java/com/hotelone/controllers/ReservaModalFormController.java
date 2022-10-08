package com.hotelone.controllers;

import com.hotelone.enums.FormaDePagamentoEnum;
import com.hotelone.services.ReservaService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public abstract class ReservaModalFormController {

    @FXML
    protected DatePicker checkinInput;

    @FXML
    protected DatePicker checkoutInput;

    @FXML
    protected Text modalTitulo;

    @FXML
    protected TextField outputHospede;

    @FXML
    protected ChoiceBox<String> pagamentoChoiceBox;

    @FXML
    protected TextField valorOutput;

    @FXML
    protected Button botaoSalvar;

    protected final ReservaService reservaService;

    protected String nomeHospede;

    public ReservaModalFormController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public abstract void initialize();

    @FXML
    public void botaoCancelarHandler(ActionEvent event) {
        getStage(event).close();
    }

    @FXML
    public abstract void botaoSalvarHandler(ActionEvent event);

    @FXML
    public abstract void checkinInputHandler(ActionEvent event);

    @FXML
    public abstract void checkoutInputHandler(ActionEvent event);

    @FXML
    public abstract void pagamentoChoiceBoxHandler(ActionEvent event);

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

    protected void configurePagamentoChoices() {
        pagamentoChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(FormaDePagamentoEnum.values()).map(v -> v.label).toList()
        ));
    }
}
