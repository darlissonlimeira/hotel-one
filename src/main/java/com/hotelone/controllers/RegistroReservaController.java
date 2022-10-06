package com.hotelone.controllers;

import com.hotelone.IndexPage;
import com.hotelone.entities.Reserva;
import com.hotelone.enums.FormaDePagamentoEnum;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.Alerta;
import com.hotelone.utils.CustomCurrency;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class RegistroReservaController {

    ReservaService reservaService = new ReservaService();

    Reserva reservaData = new Reserva();

    @FXML
    private DatePicker checkinInput;

    @FXML
    private DatePicker checkoutInput;

    @FXML
    private ChoiceBox<String> pagamentoChoiceBox;

    @FXML
    private TextField valorOutput;

    public void initialize() {
        pagamentoChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(FormaDePagamentoEnum.values()).map(v -> v.label).toList()
        ));

        checkinInput.setOnAction(event -> {
            if(checkinInput.getValue() == null) return;

            if(checkinInput.getValue().isBefore(LocalDate.now())) {
                new Alerta("Escolha uma data a partir de hoje").aviso();
                checkinInput.setValue(null);
                return;
            }

            reservaData.setCheckin(checkinInput.getValue());
        });

        checkoutInput.setOnAction(event -> {
            if(checkoutInput.getValue() == null) return;

            if(checkinInput.getValue().isAfter(checkoutInput.getValue())) {
                new Alerta("A data de saida precisa ser posterior a data de entrada.").aviso();
                checkoutInput.setValue(null);
                valorOutput.setText(null);
                return;
            }

            reservaData.setCheckout(checkoutInput.getValue());
            reservaData = reservaService.create(reservaData);
            valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
        });
    }

    @FXML
    void botaoContinuarHandler(ActionEvent event) throws IOException {
        if(valorOutput.getText().isEmpty() || pagamentoChoiceBox.getValue() == null) {
            new Alerta("Todos os campos devem ser preenchidos.").erro();
            return;
        }

        Node source = (Node) event.getSource();
        reservaData.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
        renderRegistroHospedeView(source, reservaData);
    }

    private void renderRegistroHospedeView(Node source, Reserva dadosReserva) throws IOException {
        Stage stage = (Stage) source.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(IndexPage.class.getResource("registro-hospede-view.fxml"));
        RegistroHospedeController registroHospedeController = new RegistroHospedeController(dadosReserva);
        fxmlLoader.setController(registroHospedeController);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public FormaDePagamentoEnum getFormaDePagamento(String formaDePagemnto) {
        return switch (formaDePagemnto) {
            case "Boleto" -> FormaDePagamentoEnum.BOLETO;
            case "Cartao de Credito" -> FormaDePagamentoEnum.CARTAO_DE_CREDITO;
            case "Cartao de Debito" -> FormaDePagamentoEnum.CARTAO_DE_DEBITO;
            default -> null;
        };
    }

}
