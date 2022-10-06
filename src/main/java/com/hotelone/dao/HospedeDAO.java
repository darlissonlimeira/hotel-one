package com.hotelone.dao;

import com.hotelone.entities.Hospede;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HospedeDAO {

    private final Connection connection;

    public HospedeDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Hospede> findAll() throws SQLException {
        try (PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM hospede;")) {
            ResultSet result = sqlStatement.executeQuery();

            List<Hospede> hospedeList = new ArrayList<>();

            while (result.next()) {
                Hospede hospede = hospedeMapper(result);
                hospedeList.add(hospede);
            }
            return hospedeList;
        }
    }

    public Hospede findById(String id) throws SQLException {
        try (PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM hospede WHERE id=?;")) {
            sqlStatement.setString(1, id);
            ResultSet result = sqlStatement.executeQuery();
            Hospede hospede = new Hospede();
            while (result.next()) {
                hospede = hospedeMapper(result);
            }
            return hospede;
        }
    }

    public void save(Hospede hospede) throws SQLException {
        try (PreparedStatement sqlStatement = connection.prepareStatement("INSERT INTO hospede VALUES(?, ?, ?, ?, ?, ?);")) {
            sqlStatement.setString(1, hospede.getId().toString());
            sqlStatement.setString(2, hospede.getNome());
            sqlStatement.setString(3, hospede.getSobrenome());
            sqlStatement.setDate(4, Date.valueOf(hospede.getDataNascimento()));
            sqlStatement.setString(5, hospede.getTelefone());
            sqlStatement.setString(6, hospede.getNacionalidade());
            sqlStatement.execute();
        }
    }

    public void delete(String id) throws SQLException {
        try (PreparedStatement sqlStatement = connection.prepareStatement("DELETE FROM hospede WHERE id=?;")) {
            sqlStatement.setString(1, id);
            sqlStatement.execute();
        }
    }

    public void update(Hospede hospede) throws SQLException {
        try (PreparedStatement sqlStatement = connection.prepareStatement("UPDATE hospede SET nome=?, sobrenome=?, data_nascimento=?, telefone=?, nacionalidade=? WHERE id=?;")) {
            sqlStatement.setString(1, hospede.getNome());
            sqlStatement.setString(2, hospede.getSobrenome());
            sqlStatement.setDate(3, Date.valueOf(hospede.getDataNascimento()));
            sqlStatement.setString(4, hospede.getTelefone());
            sqlStatement.setString(5, hospede.getNacionalidade());
            sqlStatement.setString(6, hospede.getId().toString());
            sqlStatement.execute();
        }
    }

    private Hospede hospedeMapper(ResultSet result) throws SQLException {
        Hospede hospede = new Hospede();
        hospede.setId(UUID.fromString(result.getString("id")));
        hospede.setNome(result.getString("nome"));
        hospede.setSobrenome(result.getString("sobrenome"));
        hospede.setDataNascimento(result.getDate("data_nascimento").toLocalDate());
        hospede.setTelefone(result.getString("telefone"));
        hospede.setNacionalidade(result.getString("nacionalidade"));
        return hospede;
    }
}
