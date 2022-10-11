package com.hotelone.utils;

import com.hotelone.entities.Hospede;
import com.hotelone.entities.Reserva;
import com.hotelone.services.HospedeService;
import com.hotelone.services.ReservaService;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.List;

public class BuscaUtils {

    DatePicker checkinInput;
    DatePicker checkoutInput;
    ReservaService reservaService;
    HospedeService hospedeService;

    public BuscaUtils(DatePicker checkinInput, DatePicker checkoutInput, ReservaService reservaService, HospedeService hospedeService) {
        this.checkinInput = checkinInput;
        this.checkoutInput = checkoutInput;
        this.reservaService = reservaService;
        this.hospedeService = hospedeService;
    }

    private boolean filterReservaCheckin(Reserva reserva) {
        verificaFiltroDeData(checkinInput);
        LocalDate date = checkinInput.getValue();
        if(date != null) date = date.minusDays(1);
        if (date == null) return true;
        return date.isBefore(reserva.getCheckin());
    }

    private boolean filterReservaCheckout(Reserva reserva) {
        verificaFiltroDeData(checkoutInput);
        LocalDate date = checkoutInput.getValue();
        if(date != null) date = date.plusDays(1);
        if (date == null) return true;
        return date.isAfter(reserva.getCheckout());
    }

    public List<Reserva> buscarReserva(String filtro, String termo) {
        List<Reserva> reservas = reservaService.findAll();
        reservas = reservas.stream().filter(this::filterReservaCheckin).toList();
        reservas = reservas.stream().filter(this::filterReservaCheckout).toList();
        reservas = switch (filtro) {
            case "Id" -> reservas.stream().filter(
                    reserva -> reserva.getId().toString().contains(termo)).toList();
            case "Hospede ID" -> reservas.stream().filter(
                    reserva -> reserva.getHospede().getId().toString().contains(termo)).toList();
            case "Tipo de pagamento" -> reservas.stream().filter(
                    reserva -> reserva.getFormaDePagamento().label.contains(termo)).toList();
            case "Valor" -> reservas.stream().filter(
                    reserva -> CustomCurrency.format(reserva.getValorTotal()).contains(termo)).toList();
            default -> reservaService.findAll();
        };
        return reservas;
    }

    public List<Hospede> buscarHospede(String filtro, String termo) {
        List<Hospede> hospedes = hospedeService.findAll();
        hospedes = switch (filtro) {
            case "Id" -> hospedes.stream().filter(
                    hospede -> hospede.getId().toString().contains(termo)).toList();
            case "Nome" -> hospedes.stream().filter(
                    hospede -> hospede.getNome().contains(termo)).toList();
            case "Sobrenome" -> hospedes.stream().filter(
                    hospede -> hospede.getSobrenome().contains(termo)).toList();
            case "Telefone" -> hospedes.stream().filter(
                    hospede -> hospede.getTelefone().contains(termo)).toList();
            case "Nacionalidade" -> hospedes.stream().filter(
                    hospede -> hospede.getNacionalidade().contains(termo)).toList();
            default -> hospedeService.findAll();
        };

        return hospedes;
    }

    private void verificaFiltroDeData(DatePicker datePicker) {
        String dataBuscarText = datePicker.getEditor().getText();
        if (dataBuscarText.isEmpty()) datePicker.setValue(null);
    }
}
