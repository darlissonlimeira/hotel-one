package com.hotelone.services;

import com.hotelone.entities.Reserva;
import com.hotelone.repository.ReservaRepository;
import com.hotelone.utils.Hospedagem;

import java.util.List;

public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService() {
        reservaRepository = new ReservaRepository();
    }

    public void save(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    public Reserva create(Reserva reserva) {
        long dias = Hospedagem.qtdDias(reserva.getCheckin(), reserva.getCheckout());
        double VALOR_DA_DIARIA = 120.00;
        double valorDataReserva = dias * VALOR_DA_DIARIA;
        reserva.setValorTotal(valorDataReserva);
        return reserva;
    }

    public void deleteOne(String id) {
        reservaRepository.delete(id);
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public void update(Reserva reserva) {
        reservaRepository.update(reserva);
    }
}
