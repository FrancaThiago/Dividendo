package br.com.alura.agenda.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Compra implements Serializable {

    private int id = 0;
    private boolean check;
    private String nome;
    private String preco;
    private String marca;

    public Compra(String nome, String preco, String marca, boolean check) {
        this.nome = nome;
        this.preco = preco;
        this.marca = marca;
        this.check = check;
    }

    public Compra() {

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getNome() {
        return nome;
    }

    public String getPreco() {
        return preco;
    }

    public boolean getCheck() {
        return check;
    }

    public String getMarca() {
        return marca;
    }


    @NonNull
    @Override
    public String toString() {
        return nome + " - " + preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean temIdValido() {
        return id > 0;
    }
}