package com.jociel.estoque.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UsuarioDAO {
    private static UsuarioDAO instancia;

    private Set<Usuario> baseUsuarios = new HashSet<>();

    private UsuarioDAO() {
        baseUsuarios = new HashSet<>();
        baseUsuarios.add(new Usuario("admin@email.com", "123"));
        baseUsuarios.add(new Usuario("jociel@email.com", "010101"));
    }

    public static UsuarioDAO getInstance() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    public Optional<Usuario> buscaPorEmail(String email) {
        return baseUsuarios.stream().filter(e -> e.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Set<Usuario> getBaseUsuarios() {
        return baseUsuarios;
    }

}
