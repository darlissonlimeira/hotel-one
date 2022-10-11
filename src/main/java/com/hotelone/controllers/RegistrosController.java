package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.entities.Reserva;
import com.hotelone.services.HospedeService;
import com.hotelone.services.ReservaService;
import com.hotelone.utils.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;

public class RegistrosController {

    @FXML
    public Button botaoVoltar = new Button();

    @FXML
    public Button botaoCopiarID = new Button();

    @FXML
    public Button botaoRemover = new Button();

    @FXML
    public Button botaoNovaReserva = new Button();

    @FXML
    private Button botaoEditar = new Button();

    @FXML
    private TextField inputBusca;

    @FXML
    private DatePicker checkinBusca = new DatePicker();

    @FXML
    private DatePicker checkoutBusca = new DatePicker();

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

    private final HospedeService hospedeService = new HospedeService();

    private final ReservaService reservaService = new ReservaService();

    @FXML
    public void initialize() {
        configureHospedesTable();
        configureReservasTable();
        loadOpcoesBuscaHospede();

        botaoCopiarID.setDisable(true);
        botaoNovaReserva.setDisable(true);
        botaoEditar.setDisable(true);
        botaoRemover.setDisable(true);

        checkinBusca.setVisible(false);
        checkoutBusca.setVisible(false);
    }

    public void tabHospedesHandler() {
        loadHospedes();
        loadOpcoesBuscaHospede();

        checkinBusca.setVisible(false);
        checkoutBusca.setVisible(false);
        botaoCopiarID.setVisible(true);
        botaoNovaReserva.setVisible(true);

        if (tabelaHospedes.getSelectionModel().getSelectedItem() == null) {
            botaoCopiarID.setDisable(true);
            botaoNovaReserva.setDisable(true);
            botaoEditar.setDisable(true);
            botaoRemover.setDisable(true);
        }
    }

    public void tabReservasHandler() {
        loadReservas();
        loadOpcoesBuscaReserva();

        checkinBusca.setVisible(true);
        checkoutBusca.setVisible(true);
        botaoCopiarID.setVisible(false);
        botaoNovaReserva.setVisible(false);

        if (tabelaReservas.getSelectionModel().getSelectedItem() == null) {
            botaoRemover.setDisable(true);
            botaoEditar.setDisable(true);
        }
    }

    public void botaoBuscarHandler() {
        BuscaUtils buscaUtils = new BuscaUtils(checkinBusca, checkoutBusca, reservaService, hospedeService);
        String termoDaBusca = inputBusca.getText();
        String filtro = opcoesDeBusca.getSelectionModel().getSelectedItem();
        int tabIndex = tabPainel.getSelectionModel().getSelectedIndex();

        if (tabIndex == 0) {
            List<Hospede> hospedeList = buscaUtils.buscarHospede(filtro, termoDaBusca);
            tabelaHospedes.setItems(FXCollections.observableArrayList(hospedeList));
        } else {
            List<Reserva> reservaList = buscaUtils.buscarReserva(filtro, termoDaBusca);
            tabelaReservas.setItems(FXCollections.observableArrayList(reservaList));
        }
    }

    public void botaoVoltarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "menu-view.fxml").update();
    }

    public void botaoEditarHandler(ActionEvent event) throws IOException {
        int tabSelecionada = tabPainel.getSelectionModel().getSelectedIndex();
        if (tabSelecionada == 0) {
            editarHospede();
        } else {
            editarReserva();
        }
    }

    public void botaoRemoverHandler() {
        int tabSelecionada = tabPainel.getSelectionModel().getSelectedIndex();
        if (tabSelecionada == 0) {
            removerHospede();
        } else {
            removerReserva();
        }
    }

    public void botaoCopiarIDHandler() {
        Hospede hospede = tabelaHospedes.getSelectionModel().getSelectedItem();
        StringSelection selection = new StringSelection(hospede.getId().toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

        botaoCopiarID.setText("Copiado!");
        botaoCopiarID.setDisable(true);
        new Timeline(new KeyFrame(Duration.millis(1500), event -> {
            botaoCopiarID.setText("Copiar ID");
            botaoCopiarID.setDisable(false);
        })).play();
    }

    public void botaoNovaReservaHandler(ActionEvent event) throws IOException {
        Hospede hospede = tabelaHospedes.getSelectionModel().getSelectedItem();
        NovaReservaController controller = new NovaReservaController(hospede, reservaService);
        new ModalRender("form-reserva-modal.fxml", controller).show();
    }

    public void removerHospede() {
        Hospede hospedeSelecionado = tabelaHospedes.getSelectionModel().getSelectedItem();

        var res = new Alerta().avisoPergunta("Deseja remover o item?");
        if (res.equals(ButtonBar.ButtonData.CANCEL_CLOSE)) return;

        hospedeService.deleteOne(hospedeSelecionado.getId().toString());
        loadHospedes();

    }

    private void removerReserva() {
        Reserva reservaSelecionada = tabelaReservas.getSelectionModel().getSelectedItem();

        var res = new Alerta().avisoPergunta("Deseja remover o item?");
        if (res.equals(ButtonBar.ButtonData.CANCEL_CLOSE)) return;

        reservaService.deleteOne(reservaSelecionada.getId().toString());
        loadReservas();
        if(tabelaReservas.getItems().isEmpty()) {
            botaoCopiarID.setDisable(true);
            botaoNovaReserva.setDisable(true);
            botaoEditar.setDisable(true);
            botaoRemover.setDisable(true);
        }
    }

    private void editarReserva() throws IOException {
        Reserva reservaSelecionada = tabelaReservas.getSelectionModel().getSelectedItem();
        EditarReservaController controller = new EditarReservaController(reservaSelecionada, reservaService);
        new ModalRender("form-reserva-modal.fxml", controller).show();
        loadReservas();
    }

    private void editarHospede() throws IOException {
        Hospede hospedeSelecionado = tabelaHospedes.getSelectionModel().getSelectedItem();
        EditarHospedeController controller = new EditarHospedeController(hospedeSelecionado);
        new ModalRender("form-hospede-modal.fxml", controller).show();
        loadHospedes();
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
            botaoNovaReserva.setDisable(false);
        });
        tabelaHospedes.getItems().addListener((ListChangeListener<? super Hospede>) change -> {
            botaoCopiarID.setDisable(true);
            botaoEditar.setDisable(true);
            botaoRemover.setDisable(true);
            botaoNovaReserva.setDisable(true);
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
            botaoEditar.setDisable(false);
        });
        tabelaReservas.getItems().addListener((ListChangeListener<? super Reserva>) change -> {
            if(tabelaReservas.getItems().isEmpty()){
                botaoRemover.setDisable(true);
                botaoEditar.setDisable(true);
            }
        });
    }

    private void loadHospedes() {
        List<Hospede> hospedeList = hospedeService.findAll();
        ObservableList<Hospede> hospedesData = FXCollections.observableArrayList(hospedeList);
        tabelaHospedes.getSelectionModel().clearSelection();
        tabelaHospedes.setItems(hospedesData);
        tabelaHospedes.refresh();
    }

    private void loadReservas() {
        List<Reserva> reservaList = reservaService.findAll();
        ObservableList<Reserva> reservasData = FXCollections.observableArrayList(reservaList);
        tabelaReservas.getSelectionModel().clearSelection();
        tabelaReservas.setItems(reservasData);
        tabelaReservas.refresh();
    }

    private void loadOpcoesBuscaHospede() {
        ObservableList<String> opcoesBuscaHospede = FXCollections.observableArrayList(
                "Id", "Nome", "Sobrenome", "Telefone", "Nacionalidade");
        opcoesDeBusca.setItems(opcoesBuscaHospede);
        opcoesDeBusca.getSelectionModel().select(1);
    }

    public void loadOpcoesBuscaReserva() {
        ObservableList<String> opcoesBuscaReserva = FXCollections.observableArrayList(
                "Id", "Hospede ID", "Tipo de pagamento", "Valor");
        opcoesDeBusca.setItems(opcoesBuscaReserva);
        opcoesDeBusca.getSelectionModel().select(1);
    }
}