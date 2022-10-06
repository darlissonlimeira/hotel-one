package com.hotelone.controllers;

import com.hotelone.config.DatabaseConfig;
import com.hotelone.utils.Alerta;
import com.hotelone.utils.AppScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPassword;

    @FXML
    public void onLoginBtnClick(ActionEvent event) {
        try {
            String username = inputUsername.getText();
            String password = inputPassword.getText();
            Connection connection = DatabaseConfig.getConnection();
            assert connection != null;
            try (PreparedStatement pst = connection.prepareStatement("SELECT * FROM administrador WHERE username=?;")) {
                pst.setString(1, username);
                ResultSet resultSet = pst.executeQuery();
                if(!resultSet.next()) {
                    new Alerta("Crendenciais inválidas").aviso();
                    return;
                };
                if (!resultSet.getString("password").equals(password)) {
                    new Alerta("Crendenciais inválidas").aviso();
                    return;
                }
            }

            Node source = (Node) event.getSource();
            new AppScene(source, "menu-view.fxml").update();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    public void onCancelBtnClick(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            new AppScene(source, "tela-principal-view.fxml").update();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Algo deu errado");
        }
    }
}
