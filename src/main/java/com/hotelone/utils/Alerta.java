package com.hotelone.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class Alerta{

    private Alert alerta = new Alert(Alert.AlertType.NONE);

    public Alerta(String mensagem){
        alerta.setTitle("Alerta!");
        alerta.setContentText(mensagem);
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.setHeaderText(null);
    }

    public Alerta(){}

    public void aviso() {
        alerta.setAlertType(Alert.AlertType.WARNING);
        alerta.showAndWait();
    }

    public void erro() {
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.showAndWait();
    }

    public void confirmacao() {
        alerta.setAlertType(Alert.AlertType.CONFIRMATION);
        alerta.showAndWait();
    }

    public ButtonBar.ButtonData avisoPergunta(String pergunta) {
        alerta = new Alert(Alert.AlertType.WARNING, pergunta,
                new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE),
                new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE));
        alerta.setTitle("Alerta!");
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.setHeaderText(null);
        var reposta = alerta.showAndWait();
        if(reposta.isEmpty()) return null;
        return reposta.get().getButtonData();
    }
}
