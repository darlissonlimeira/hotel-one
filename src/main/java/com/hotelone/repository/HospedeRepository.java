package com.hotelone.repository;

import com.hotelone.config.DatabaseConfig;
import com.hotelone.dao.HospedeDAO;
import com.hotelone.entities.Hospede;

import java.sql.SQLException;
import java.util.List;

public class HospedeRepository {
    private final HospedeDAO hospedeDAO;

    public HospedeRepository() {
        this.hospedeDAO = new HospedeDAO(DatabaseConfig.getConnection());
    }

    public List<Hospede> findAll() {
        try {
            return this.hospedeDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Hospede entity) {
        try {
            this.hospedeDAO.save(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Hospede findById(String id) {
        try {
            return this.hospedeDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Hospede entity) {
        try {
            this.hospedeDAO.update(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        try {
            this.hospedeDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
