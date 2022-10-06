package com.hotelone.services;

import com.hotelone.entities.Hospede;
import com.hotelone.repository.HospedeRepository;

import java.util.List;

public class HospedeService {
    private final HospedeRepository hospedeRepository;

    public HospedeService() {
        hospedeRepository = new HospedeRepository();
    }

    public void save(Hospede hospede) {
        hospedeRepository.save(hospede);
    }

    public void update(Hospede hospede) {
        hospedeRepository.update(hospede);
    }

    public void deleteOne(String id) {
        hospedeRepository.delete(id);
    }

    public List<Hospede> findAll() {
        return hospedeRepository.findAll();
    }

    public Hospede findById(String id) {
        return hospedeRepository.findById(id);
    }
}
