package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.entities.Reserva;
import com.hotelone.enums.FormaDePagamentoEnum;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.Alerta;
import com.hotelone.utils.CustomCurrency;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;

public class NovaReservaController {

    @FXML
    private DatePicker checkinInput;

    @FXML
    private DatePicker checkoutInput;

    @FXML
    private Text modalTitulo;

    @FXML
    private TextField outputHospede;

    @FXML
    private ChoiceBox<String> pagamentoChoiceBox;

    @FXML
    private TextField valorOutput;

    private final Hospede hospede;

    private Reserva reserva;

    private Reserva reservaData = new Reserva();

    private final ReservaService reservaService;


    public NovaReservaController(Hospede hospede, ReservaService reservaService) {
        this.hospede = hospede;
        this.reservaService = reservaService;
    }

    public NovaReservaController(Reserva reserva, ReservaService reservaService) {
        this.hospede = reserva.getHospede();
        this.reserva = reserva;
        this.reservaService = reservaService;
    }

    public void initialize () {
        if(reserva != null) {
            reservaData = reserva;
            checkinInput.setValue(reservaData.getCheckin());
            checkoutInput.setValue(reservaData.getCheckout());
            valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
            pagamentoChoiceBox.getSelectionModel().select(reservaData.getFormaDePagamento().label);
        }
        modalTitulo.setText("Nova Reserva");
        outputHospede.setText(hospede.getNome());

        pagamentoChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(FormaDePagamentoEnum.values()).map(v -> v.label).toList()
        ));

        checkinInput.setOnAction(event -> {
            Stage stage = getStage(event);
            if(checkinInput.getValue() == null) return;

            if(checkinInput.getValue().isBefore(LocalDate.now())) {
                stage.setAlwaysOnTop(false);
                new Alerta("Escolha uma data a partir de hoje").aviso();
                stage.setAlwaysOnTop(true);
                checkinInput.setValue(null);
                return;
            }

            reservaData.setCheckin(checkinInput.getValue());
        });

        checkoutInput.setOnAction(event -> {
            Stage stage = getStage(event);

            if(checkoutInput.getValue() == null) return;

            if(checkinInput.getValue().isAfter(checkoutInput.getValue())) {
                stage.setAlwaysOnTop(false);
                new Alerta("A data de saida precisa ser posterior a data de entrada.").aviso();
                stage.setAlwaysOnTop(true);
                checkoutInput.setValue(null);
                valorOutput.setText(null);
                return;
            }

            reservaData.setCheckout(checkoutInput.getValue());
            reservaData = reserva != null ? reserva : reservaService.create(reservaData);
            valorOutput.setText(CustomCurrency.format(reservaData.getValorTotal()));
        });
    }

    @FXML
    public void botaoCancelarHandler(ActionEvent event) {
        getStage(event).close();
    }

    @FXML
    public void botaoSalvarHandler(ActionEvent event) {
        Stage stage = getStage(event);
        if(valorOutput.getText().isEmpty() || pagamentoChoiceBox.getValue() == null) {
            stage.setAlwaysOnTop(false);
            new Alerta("Todos os campos devem ser preenchidos.").erro();
            stage.setAlwaysOnTop(false);
            return;
        }

        reservaData.setHospede(this.hospede);
        reservaData.setFormaDePagamento(getFormaDePagamento(pagamentoChoiceBox.getValue()));
        if(reserva != null) {
            reservaService.update(reservaData);
        } else {
            reservaService.save(reservaData);
        }
        stage.close();
    }

    private FormaDePagamentoEnum getFormaDePagamento(String formaDePagemnto) {
        return switch (formaDePagemnto) {
            case "Boleto" -> FormaDePagamentoEnum.BOLETO;
            case "Cartao de Credito" -> FormaDePagamentoEnum.CARTAO_DE_CREDITO;
            case "Cartao de Debito" -> FormaDePagamentoEnum.CARTAO_DE_DEBITO;
            default -> null;
        };
    }

    private Stage getStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}
