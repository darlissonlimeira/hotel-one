package com.hotelone.dao;

import com.hotelone.entities.Hospede;
import com.hotelone.entities.Reserva;
import com.hotelone.enums.FormaDePagamentoEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservaDAO {
    private final Connection connection;

    public ReservaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Reserva> findAll() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM reserva;")) {
            List<Reserva> reservaList = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Reserva reserva = reservaMapper(result);
                reservaList.add(reserva);
            }
            return reservaList;
        }
    }

    public void delete(String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM reserva WHERE id=?;")) {
            statement.setString(1, id);
            statement.execute();
        }
    }

    public void save(Reserva reserva) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO reserva VALUES(?, ?, ?, ?, ?, ?);")) {
            statement.setString(1, reserva.getId().toString());
            statement.setDate(2, Date.valueOf(reserva.getCheckin()));
            statement.setDate(3, Date.valueOf(reserva.getCheckout()));
            statement.setString(4, reserva.getFormaDePagamento().toString());
            statement.setDouble(5, reserva.getValorTotal());
            statement.setString(6, reserva.getHospede().getId().toString());
            statement.execute();
        }
    }

    public void update(Reserva reserva) throws SQLException {
        try (PreparedStatement sqlStatement = connection.prepareStatement("UPDATE reserva SET checkin=?, checkout=?, forma_pagamento=?, valor=? WHERE id=?;")) {
            sqlStatement.setDate(1, Date.valueOf(reserva.getCheckin()));
            sqlStatement.setDate(2, Date.valueOf(reserva.getCheckout()));
            sqlStatement.setString(3, reserva.getFormaDePagamento().toString());
            sqlStatement.setDouble(4, reserva.getValorTotal());
            sqlStatement.setString(5, reserva.getId().toString());
            sqlStatement.execute();
        }
    }

    private Reserva reservaMapper(ResultSet result) throws SQLException {
        HospedeDAO hospedeDAO = new HospedeDAO(connection);
        Hospede hospede = hospedeDAO.findById(result.getString("hospede_id"));
        Reserva reserva = new Reserva();
        reserva.setId(UUID.fromString(result.getString("id")));
        reserva.setCheckin(result.getDate("checkin").toLocalDate());
        reserva.setCheckout(result.getDate("checkout").toLocalDate());
        reserva.setFormaDePagamento(FormaDePagamentoEnum.valueOf(result.getString("forma_pagamento")));
        reserva.setValorTotal(result.getDouble("valor"));
        reserva.setHospede(hospede);
        return reserva;
    }
}
