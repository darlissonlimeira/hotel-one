package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.services.HospedeService;
import com.hotelone.utils.Alerta;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditarHospedeModalFormController {

    private final Hospede hospedeData;

    @FXML
    private TextField inputNome;

    @FXML
    private TextField inputSobrenome;

    @FXML
    private TextField inputTelefone;

    @FXML
    private DatePicker inputDataNascimento;

    @FXML
    private ChoiceBox<String> inputNacionalidade;

    public EditarHospedeModalFormController(Hospede hospedeData) {
        this.hospedeData = hospedeData;
    }


    public void initialize() {
        loadListaNacionalidades();

        inputNome.setText(hospedeData.getNome());
        inputSobrenome.setText(hospedeData.getSobrenome());
        inputDataNascimento.setValue(hospedeData.getDataNascimento());
        inputTelefone.setText(hospedeData.getTelefone());
        inputNacionalidade.getSelectionModel().select(
                inputNacionalidade.getItems().indexOf(hospedeData.getNacionalidade()));
    }

    private void loadListaNacionalidades() {
        String[] nacionalidades = new String[]{"alemão", "andorrano", "angolano", "antiguano", "saudita", "argelino", "argentino", "armênio", "australiano", "austríaco", "azerbaijano", "bahamense", "bangladês, bangladense", "barbadiano", "bahreinita", "belga", "belizenho", "beninês", "belarusso", "boliviano", "bósnio", "botsuanês", "brasileiro", "bruneíno", "búlgaro", "burkineonse, burkinabé", "burundês", "butanês", "cabo-verdiano", "camerounês", "cambojano", "catariano", "canadense", "cazaque", "chadiano", "chileno", "chinês", "cipriota", "colombiano", "comoriano", "congolês", "congolês", "sul-coreano", "norte-coreano", "costa-marfinense, marfinense", "costa-ricense", "croata", "cubano", "dinamarquês", "djiboutiano", "dominiquense", "egípcio", "salvadorenho", "emiradense, emirático", "equatoriano", "eritreu", "eslovaco", "esloveno", "espanhol", "estadunidense, (norte-)americano", "estoniano", "etíope", "fijiano", "filipino", "finlandês", "francês", "gabonês", "gambiano", "ganês ou ganense", "georgiano", "granadino", "grego", "guatemalteco", "guianês", "guineense", "guineense, bissau-guineense", "equato-guineense", "haitiano", "hondurenho", "húngaro", "iemenita", "cookiano", "marshallês", "salomonense", "indiano", "indonésio", "iraniano", "iraquiano", "irlandês", "islandês", "34", "jamaicano", "japonês", "jordaniano", "kiribatiano", "kuwaitiano", "laosiano", "lesotiano", "letão", "libanês", "liberiano", "líbio", "liechtensteiniano", "lituano", "luxemburguês", "macedônio", "madagascarense", "malásio37", "malawiano", "maldivo", "maliano", "maltês", "marroquino", "mauriciano", "mauritano", "mexicano", "myanmarense", "micronésio", "moçambicano", "moldovo", "monegasco", "mongol", "montenegrino", "namibiano", "nauruano", "nepalês", "nicaraguense", "nigerino", "nigeriano", "niuiano", "norueguês", "neozelandês", "omani", "neerlandês", "palauano", "palestino", "panamenho", "papua, papuásio", "paquistanês", "paraguaio", "peruano", "polonês, polaco", "português", "queniano", "quirguiz", "britânico", "centro-africano", "tcheco", "dominicano", "romeno", "ruandês", "russo", "samoano", "santa-lucense", "são-cristovense", "samarinês", "santomense", "são-vicentino", "seichelense", "senegalês", "sérvio", "singapurense", "sírio", "somaliano, somali", "sri-lankês", "suázi", "sudanês", "sul-sudanês", "sueco", "suíço", "surinamês", "tajique", "tailandês", "tanzaniano", "timorense", "togolês", "tonganês", "trinitário", "tunisiano", "turcomeno", "turco", "tuvaluano", "ucraniano", "ugandês", "uruguaio", "uzbeque", "vanuatuense", "vaticano", "venezuelano", "vietnamita", "zambiano", "zimbabueano"};
        inputNacionalidade.setItems(FXCollections.observableArrayList(nacionalidades));
    }

    @FXML
    void inputDataNascimentoHandler() {
        LocalDate limite = LocalDate.now().minusYears(18);
        if (inputDataNascimento.getValue().isAfter(limite)) {
            new Alerta("O hospede não pode ser menor de idade.").aviso();
            inputDataNascimento.setValue(null);
        }
    }

    @FXML
    void botaoCancelarHandler(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void botaoSalvarHandler(ActionEvent event) {
        if (inputNome.getText().isEmpty() || inputSobrenome.getText().isEmpty() ||
                inputDataNascimento.getValue() == null || inputTelefone.getText().isEmpty() || inputNacionalidade.getValue() == null) {

            new Alerta("Todos os campos devem ser preenchidos.").erro();
            return;
        }

        hospedeData.setNome(inputNome.getText());
        hospedeData.setSobrenome(inputSobrenome.getText());
        hospedeData.setDataNascimento(inputDataNascimento.getValue());
        hospedeData.setTelefone(inputTelefone.getText());
        hospedeData.setNacionalidade(inputNacionalidade.getValue());

        HospedeService hospedeService = new HospedeService();
        hospedeService.update(hospedeData);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
