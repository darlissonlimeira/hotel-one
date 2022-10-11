package com.hotelone;

import com.hotelone.config.DatabaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Index extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Index.class.getResource("index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Sistema Hotel ONE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConfig.getConnection();
        assert connection != null;
        inicializarTabelas(connection);
        inicializarCredenciaisDeAcesso(connection);
        launch();
    }

    public static void inicializarCredenciaisDeAcesso(Connection connection) {
        try (PreparedStatement pst = connection.prepareStatement("INSERT IGNORE INTO administrador VALUES (?, ?)")) {
            pst.setString(1, "admin");
            pst.setString(2, "admin");
            pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void inicializarTabelas(Connection connection) throws SQLException {
        String queryTabelaHospede = "CREATE TABLE IF NOT EXISTS hospede (id VARCHAR(255) PRIMARY KEY NOT NULL, nome VARCHAR(50) NOT NULL, sobrenome VARCHAR(100) NOT NULL, data_nascimento DATE NOT NULL, telefone VARCHAR(20) NOT NULL, nacionalidade VARCHAR(100) NOT NULL);";
        String queryTabelaReserva = "CREATE TABLE IF NOT EXISTS reserva (id VARCHAR(255) PRIMARY KEY NOT NULL, checkin DATE NOT NULL, checkout DATE NOT NULL, forma_pagamento VARCHAR(100), valor DECIMAL NOT NULL, CHECK (valor>0), hospede_id VARCHAR(255), FOREIGN KEY (hospede_id) REFERENCES hospede(id) ON DELETE CASCADE ON UPDATE CASCADE);";
        String queryTabelaAdministrador = "CREATE TABLE IF NOT EXISTS administrador (username VARCHAR(255) PRIMARY KEY NOT NULL, password VARCHAR(255) NOT NULL);";
        try (Statement st = connection.createStatement()) {
            st.execute(queryTabelaAdministrador);
            st.execute(queryTabelaHospede);
            st.execute(queryTabelaReserva);
        }
    }
}
