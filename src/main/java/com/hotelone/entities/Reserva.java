package com.hotelone.entities;

import com.hotelone.enums.FormaDePagamentoEnum;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reserva {
    private UUID id = UUID.randomUUID();
    private Hospede hospede;
    private LocalDate checkin;
    private LocalDate checkout;
    private FormaDePagamentoEnum formaDePagamento;
    private double valorTotal;

    public Reserva() {
    }

    public Reserva(Hospede hospede, LocalDate checkin, LocalDate checkout, FormaDePagamentoEnum formaDePagamento, double valorTotal) {
        this.hospede = hospede;
        this.checkin = checkin;
        this.checkout = checkout;
        this.formaDePagamento = formaDePagamento;
        this.valorTotal = valorTotal;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public FormaDePagamentoEnum getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamentoEnum formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return id.equals(reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
