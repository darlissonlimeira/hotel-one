package com.hotelone.utils;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class Alerta{

    private String mensagem;

    public Alerta(String mensagem){
        this.mensagem = mensagem;
    }

    public void aviso() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Mensagem!");
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public void erro() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Mensagem!");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public void confirmacao() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Mensagem!");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
