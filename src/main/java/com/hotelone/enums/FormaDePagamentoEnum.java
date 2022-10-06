package com.hotelone.enums;

public enum FormaDePagamentoEnum {
    CARTAO_DE_CREDITO("Cartao de Credito"),
    CARTAO_DE_DEBITO("Cartao de Debito"),
    BOLETO("Boleto");

    public final String label;
    FormaDePagamentoEnum(String label) {
        this.label = label;
    }
}
