package com.example.projetojavafx.model.entities;

import java.util.Arrays;
import java.util.Objects;

public class Professor {
    private Integer matricula;
    private String nome;
   private byte foto;

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor professor)) return false;
        return Objects.equals(getMatricula(), professor.getMatricula());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatricula());
    }

    @Override
    public String toString() {
        return "Professor{" +
                "matricula=" + matricula +
                ", nome='" + nome +
                '}';
    }
}
