package com.jociel.estoque.util;

public enum Constantes {


    REGEX_EMAIL("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"),
    REGEX_SENHA("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=-]).{6,}$");

    private final String valor;

    Constantes(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
