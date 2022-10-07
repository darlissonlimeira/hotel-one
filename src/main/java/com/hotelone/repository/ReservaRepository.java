package com.hotelone.repository;

import com.hotelone.config.DatabaseConfig;
import com.hotelone.dao.ReservaDAO;
import com.hotelone.entities.Reserva;

import java.sql.SQLException;
import java.util.List;

public class ReservaRepository {
    private final ReservaDAO reservaDAO;

    public ReservaRepository() {
        this.reservaDAO = new ReservaDAO(DatabaseConfig.getConnection());
    }

    public List<Reserva> findAll() {
        try {
            return reservaDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Reserva entity) {
        try {
            reservaDAO.save(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        try {
            reservaDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Reserva reserva) {
        try {
            reservaDAO.update(reserva);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
