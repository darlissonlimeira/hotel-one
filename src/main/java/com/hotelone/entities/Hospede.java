package com.hotelone.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Hospede {
    private UUID id = UUID.randomUUID();
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String telefone;
    private String nacionalidade;

    public Hospede() {}

    public Hospede(String nome, String sobrenome, LocalDate dataNascimento, String telefone, String nacionalidade) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.nacionalidade = nacionalidade;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospede hospede = (Hospede) o;
        return id.equals(hospede.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
