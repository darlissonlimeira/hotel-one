package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.services.HospedeService;
import com.hotelone.utils.Alerta;
import com.hotelone.utils.SceneRender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public abstract class AbstractHospedeController {
    @FXML
    protected TextField inputNome;

    @FXML
    protected TextField inputSobrenome;

    @FXML
    protected TextField inputTelefone;

    @FXML
    protected DatePicker inputDataNascimento;

    @FXML
    protected ChoiceBox<String> inputNacionalidade;

    protected Hospede hospedeData = new Hospede();

    protected final HospedeService hospedeService;

    public AbstractHospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    public void initialize() {
        loadListaNacionalidades();
    }

    public void botaoCancelarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "menu-view.fxml").update();
    }

    @FXML
    public void inputNomeHandler(KeyEvent event) {
        if(inputNome.getText().length() < 2) {
            desabilitarBotao();
            return;
        }
        inputSobrenome.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public void inputSobrenomeHandler(KeyEvent event) {
        if(inputSobrenome.getText().length() < 2) {
            desabilitarBotao();
            return;
        }
        inputDataNascimento.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public void inputDataNascimentoHandler(ActionEvent event)  {
        if(inputDataNascimento.getValue() == null) return;
        Stage modal = getStage(event);
        LocalDate limite = LocalDate.now().minusYears(18);
        if(inputDataNascimento.getValue().isAfter(limite)) {
            modal.setAlwaysOnTop(false);
            new Alerta("O hospede não pode ser menor de idade.").aviso();
            modal.setAlwaysOnTop(true);
            inputDataNascimento.setValue(hospedeData.getDataNascimento());
            desabilitarBotao();
            return;
        }
        inputTelefone.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public void inputTelefoneHandler(KeyEvent event) {
        if(inputTelefone.getText().length() < 10) {
            desabilitarBotao();
            return;
        }
        inputNacionalidade.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public abstract void inputNacionalidadeHandler(ActionEvent event);

    public abstract void desabilitarBotao();

    public abstract void habilitarBotao();

    private void loadListaNacionalidades(){
        String[] nacionalidades = new String[]{"alemão", "andorrano", "angolano", "antiguano", "saudita", "argelino", "argentino", "armênio", "australiano", "austríaco", "azerbaijano", "bahamense", "bangladês, bangladense", "barbadiano", "bahreinita", "belga", "belizenho", "beninês", "belarusso", "boliviano", "bósnio", "botsuanês", "brasileiro", "bruneíno", "búlgaro", "burkineonse, burkinabé", "burundês", "butanês", "cabo-verdiano", "camerounês", "cambojano", "catariano", "canadense", "cazaque", "chadiano", "chileno", "chinês", "cipriota", "colombiano", "comoriano", "congolês", "congolês", "sul-coreano", "norte-coreano", "costa-marfinense, marfinense", "costa-ricense", "croata", "cubano", "dinamarquês", "djiboutiano", "dominiquense", "egípcio", "salvadorenho", "emiradense, emirático", "equatoriano", "eritreu", "eslovaco", "esloveno", "espanhol", "estadunidense, (norte-)americano", "estoniano", "etíope", "fijiano", "filipino", "finlandês", "francês", "gabonês", "gambiano", "ganês ou ganense", "georgiano", "granadino", "grego", "guatemalteco", "guianês", "guineense", "guineense, bissau-guineense", "equato-guineense", "haitiano", "hondurenho", "húngaro", "iemenita", "cookiano", "marshallês", "salomonense", "indiano", "indonésio", "iraniano", "iraquiano", "irlandês", "islandês", "34", "jamaicano", "japonês", "jordaniano", "kiribatiano", "kuwaitiano", "laosiano", "lesotiano", "letão", "libanês", "liberiano", "líbio", "liechtensteiniano", "lituano", "luxemburguês", "macedônio", "madagascarense", "malásio37", "malawiano", "maldivo", "maliano", "maltês", "marroquino", "mauriciano", "mauritano", "mexicano", "myanmarense", "micronésio", "moçambicano", "moldovo", "monegasco", "mongol", "montenegrino", "namibiano", "nauruano", "nepalês", "nicaraguense", "nigerino", "nigeriano", "niuiano", "norueguês", "neozelandês", "omani", "neerlandês", "palauano", "palestino", "panamenho", "papua, papuásio", "paquistanês", "paraguaio", "peruano", "polonês, polaco", "português", "queniano", "quirguiz", "britânico", "centro-africano", "tcheco", "dominicano", "romeno", "ruandês", "russo", "samoano", "santa-lucense", "são-cristovense", "samarinês", "santomense", "são-vicentino", "seichelense", "senegalês", "sérvio", "singapurense", "sírio", "somaliano, somali", "sri-lankês", "suázi", "sudanês", "sul-sudanês", "sueco", "suíço", "surinamês", "tajique", "tailandês", "tanzaniano", "timorense", "togolês", "tonganês", "trinitário", "tunisiano", "turcomeno", "turco", "tuvaluano", "ucraniano", "ugandês", "uruguaio", "uzbeque", "vanuatuense", "vaticano", "venezuelano", "vietnamita", "zambiano", "zimbabueano"};
        inputNacionalidade.setItems(FXCollections.observableArrayList(nacionalidades));
    }

    protected void setHospedeData() {
        hospedeData.setNome(inputNome.getText());
        hospedeData.setSobrenome(inputSobrenome.getText());
        hospedeData.setDataNascimento(inputDataNascimento.getValue());
        hospedeData.setTelefone(inputTelefone.getText());
        hospedeData.setNacionalidade(inputNacionalidade.getValue());
    }

    public Stage getStage (Event event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}
