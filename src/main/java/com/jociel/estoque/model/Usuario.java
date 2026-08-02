package com.jociel.estoque.model;

public class Usuario {

    private String email;
    private String senha;

    public Usuario() {

    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
