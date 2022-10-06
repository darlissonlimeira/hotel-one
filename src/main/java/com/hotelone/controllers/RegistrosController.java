package com.hotelone.controllers;

import com.hotelone.IndexPage;
import com.hotelone.entities.Hospede;
import com.hotelone.entities.Reserva;
import com.hotelone.repository.HospedeRepository;
import com.hotelone.repository.ReservaRepository;
import com.hotelone.services.HospedeService;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.AppScene;
import com.hotelone.utils.CustomCurrency;
import com.hotelone.utils.CustomDate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RegistrosController {

    @FXML
    public Button botaoVoltar = new Button();

    @FXML
    public Button botaoCopiarID = new Button();

    @FXML
    public Button botaoRemover = new Button();

    @FXML
    private Button botaoEditar = new Button();

    @FXML
    private TextField inputBusca;

    @FXML
    private ChoiceBox<String> opcoesDeBusca = new ChoiceBox<>();

    @FXML
    private TableView<Hospede> tabelaHospedes;

    @FXML
    private TableColumn<Hospede, String> hospedeId;

    @FXML
    private TableColumn<Hospede, String> hospedeNacionalidade;

    @FXML
    private TableColumn<Hospede, String> hospedeNome;

    @FXML
    private TableColumn<Hospede, String> hospedeSobrenome;

    @FXML
    private TableColumn<Hospede, String> hospedeNascimento;

    @FXML
    private TableColumn<Hospede, String> hospedeTelefone;

    @FXML
    private TableView<Reserva> tabelaReservas;

    @FXML
    private TableColumn<Reserva, String> reservaCheckin;

    @FXML
    private TableColumn<Reserva, String> reservaCheckout;

    @FXML
    private TableColumn<Reserva, String> reservaId;

    @FXML
    private TableColumn<Reserva, String> reservaHospede;

    @FXML
    private TableColumn<Reserva, String> reservaPagamento;

    @FXML
    private TableColumn<Reserva, String> reservaValor;

    @FXML
    private TabPane tabPainel;

    @FXML
    private DatePicker checkinBusca = new DatePicker();

    @FXML
    private DatePicker checkoutBusca = new DatePicker();

    private final HospedeService hospedeService = new HospedeService();

    private final ReservaService reservaService = new ReservaService();

    @FXML
    public void initialize() {
        configureHospedesTable();
        configureReservasTable();
    }


    public void tabHospedesHandler() {
        ObservableList<String> opcoesBuscaHospede = FXCollections.observableArrayList(
                "Id", "Nome", "Sobrenome", "Telefone", "Nacionalidade");
        opcoesDeBusca.setItems(opcoesBuscaHospede);
        opcoesDeBusca.getSelectionModel().select(0);

        checkinBusca.setVisible(false);
        checkoutBusca.setVisible(false);
        botaoCopiarID.setVisible(true);
        botaoEditar.setVisible(true);

        if (tabelaHospedes.getSelectionModel().getSelectedItem() == null) {
            botaoCopiarID.setDisable(true);
            botaoEditar.setDisable(true);
            botaoRemover.setDisable(true);
        }

        loadHospedes();
    }

    public void tabReservasHandler() {
        ObservableList<String> opcoesBuscaReserva = FXCollections.observableArrayList(
                "Id", "Tipo de pagamento", "Valor");
        opcoesDeBusca.setItems(opcoesBuscaReserva);
        opcoesDeBusca.getSelectionModel().select(0);

        checkinBusca.setVisible(true);
        checkoutBusca.setVisible(true);
        botaoCopiarID.setVisible(false);
        botaoEditar.setVisible(false);

        if (tabelaReservas.getSelectionModel().getSelectedItem() == null) {
            botaoRemover.setDisable(true);
        }

        loadReservas();
    }

    private void configureHospedesTable() {
        hospedeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        hospedeNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        hospedeSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        hospedeNascimento.setCellValueFactory(data -> new SimpleStringProperty(CustomDate.format(data.getValue().getDataNascimento())));
        hospedeTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        hospedeNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));

        tabelaHospedes.getSelectionModel().selectedItemProperty().addListener((observableValue, hospede, t1) -> {
            botaoCopiarID.setDisable(false);
            botaoEditar.setDisable(false);
            botaoRemover.setDisable(false);
        });
    }

    private void configureReservasTable() {
        reservaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservaHospede.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHospede().getId().toString()));
        reservaCheckin.setCellValueFactory(data -> new SimpleStringProperty(CustomDate.format(data.getValue().getCheckin())));
        reservaCheckout.setCellValueFactory(data -> new SimpleStringProperty(CustomDate.format(data.getValue().getCheckout())));
        reservaPagamento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFormaDePagamento().label));
        reservaValor.setCellValueFactory(data -> new SimpleStringProperty(CustomCurrency.format(data.getValue().getValorTotal())));

        tabelaReservas.getSelectionModel().selectedItemProperty().addListener((observableValue, reserva, t1) -> {
            botaoRemover.setDisable(false);
        });

    }

    private void loadHospedes() {
        List<Hospede> hospedeList = hospedeService.findAll();
        ObservableList<Hospede> hospedesData = FXCollections.observableArrayList(hospedeList);
        tabelaHospedes.getSelectionModel().clearSelection();
        tabelaHospedes.setItems(hospedesData);
    }

    private void loadReservas() {
        List<Reserva> reservaList = reservaService.findAll();
        ObservableList<Reserva> reservasData = FXCollections.observableArrayList(reservaList);
        tabelaReservas.getSelectionModel().clearSelection();
        tabelaReservas.setItems(reservasData);
    }

    private List<Hospede> buscarHospedePor(String filtro, String termo) {
        List<Hospede> hospedes = hospedeService.findAll();
        hospedes = switch (filtro) {
            case "Id" -> hospedes.stream().filter(
                    hospede -> hospede.getId().toString().contains(termo)).toList();
            case "Nome" -> hospedes.stream().filter(
                    hospede -> hospede.getNome().contains(termo)).toList();
            case "Sobrenome" -> hospedes.stream().filter(
                    hospede -> hospede.getSobrenome().contains(termo)).toList();
            case "Telefone" -> hospedes.stream().filter(
                    hospede -> hospede.getTelefone().contains(termo)).toList();
            case "Nacionalidade" -> hospedes.stream().filter(
                    hospede -> hospede.getNacionalidade().contains(termo)).toList();
            default -> hospedeService.findAll();
        };

        return hospedes;
    }

    private List<Reserva> buscarReservaPor(String filtro, String termo) {
        List<Reserva> reservas = reservaService.findAll();
        tabelaReservas.setItems(FXCollections.observableArrayList(reservas));

        verificaFiltroDeData(checkinBusca);
        verificaFiltroDeData(checkoutBusca);

        reservas = reservas.stream().filter(this::filterCheckin).toList();
        reservas = reservas.stream().filter(this::filterCheckout).toList();

        reservas = switch (filtro) {
            case "Id" -> reservas.stream().filter(
                    hospede -> hospede.getId().toString().contains(termo)).toList();
            case "Tipo de pagamento" -> reservas.stream().filter(
                    hospede -> hospede.getFormaDePagamento().label.contains(termo)).toList();
            case "Valor" -> reservas.stream().filter(
                    hospede -> CustomCurrency.format(hospede.getValorTotal()).contains(termo)).toList();
            default -> reservaService.findAll();
        };
        return reservas;
    }

    private boolean filterCheckin(Reserva reserva) {
        LocalDate date = checkinBusca.getValue();
        if (date == null) return true;
        return date.isBefore(reserva.getCheckin());
    }

    private boolean filterCheckout(Reserva reserva) {
        LocalDate date = checkoutBusca.getValue();
        if (date == null) return true;
        return date.isAfter(reserva.getCheckout());
    }


    public void verificaFiltroDeData(DatePicker datePicker) {
        String dataBuscarText = datePicker.getEditor().getText();
        if (dataBuscarText.isEmpty()) datePicker.setValue(null);
    }

    public void removeItemSelecionadoHandler() {
        int tabSelecionada = tabPainel.getSelectionModel().getSelectedIndex();

        if (tabSelecionada == 0) {
            removeHospedeSelecionado();
        } else {
            removeReservaSelecionado();
        }
    }


    public void removeHospedeSelecionado() {
        Hospede hospedeSelecionado = tabelaHospedes.getSelectionModel().getSelectedItem();
        if (hospedeSelecionado == null) return;

        var res = mostrarConfirmacao().get();
        if (res.getButtonData().equals(ButtonBar.ButtonData.CANCEL_CLOSE)) return;

        hospedeService.deleteOne(hospedeSelecionado.getId().toString());
        tabelaHospedes.setItems(FXCollections.observableArrayList(hospedeService.findAll()));
    }

    private void removeReservaSelecionado() {
        Reserva reservaSelecionada = tabelaReservas.getSelectionModel().getSelectedItem();
        if (reservaSelecionada == null) return;

        var res = mostrarConfirmacao().get();
        if (res.getButtonData().equals(ButtonBar.ButtonData.CANCEL_CLOSE)) return;

        reservaService.deleteOne(reservaSelecionada.getId().toString());
        tabelaReservas.setItems(FXCollections.observableArrayList(reservaService.findAll()));
    }

    public void botaoBuscarHandler() {
        String termoDaBusca = inputBusca.getText();
        String filtro = opcoesDeBusca.getSelectionModel().getSelectedItem();
        int tabIndex = tabPainel.getSelectionModel().getSelectedIndex();

        if (tabIndex == 0) {
            List<Hospede> hospedeList = buscarHospedePor(filtro, termoDaBusca);
            tabelaHospedes.setItems(FXCollections.observableArrayList(hospedeList));
        } else {
            List<Reserva> reservaList = buscarReservaPor(filtro, termoDaBusca);
            tabelaReservas.setItems(FXCollections.observableArrayList(reservaList));
        }
    }

    public void botaoEditarHandler() {
        Hospede hospedeSelecionado = tabelaHospedes.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IndexPage.class.getResource("editar-registro-hospede-view.fxml"));
            fxmlLoader.setController(new EditarHospedeController(hospedeSelecionado));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setScene(scene);
            stage.showAndWait();
            tabelaHospedes.refresh();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private Optional<ButtonType> mostrarConfirmacao() {
        Alert alerta = new Alert(
                Alert.AlertType.WARNING, "Confirme para remover.",
                new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE),
                new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE));
        alerta.setTitle("Mensagem!");
        alerta.setHeaderText(null);
        return alerta.showAndWait();
    }

    public void botaoVoltarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new AppScene(source, "menu-view.fxml").update();
    }

    public void botaoCopiarIDHandler() {
        Hospede hospede = tabelaHospedes.getSelectionModel().getSelectedItem();
        StringSelection selection = new StringSelection(hospede.getId().toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

        botaoCopiarID.setText("Copiado!");
        botaoCopiarID.setDisable(true);
        new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            botaoCopiarID.setText("Copiar ID");
            botaoCopiarID.setDisable(false);
        })).play();
    }
}