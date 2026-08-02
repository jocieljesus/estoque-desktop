package com.jociel.estoque.service;

import com.jociel.estoque.model.Usuario;
import com.jociel.estoque.model.UsuarioDAO;

import java.util.Optional;
import java.util.Random;

public class RecuperacaoSenhaService {
    private Usuario usuarioAlvo;
    private String codigoGerado;


    public String solicitarRecuperacao(String email, UsuarioDAO baseUsuarios) {
        Optional<Usuario> usuarioEncontrado = baseUsuarios.buscaPorEmail(email);
        if (usuarioEncontrado.isEmpty()) return null;


        this.usuarioAlvo = usuarioEncontrado.get();
        this.codigoGerado = gerarCodigo();
        return this.codigoGerado;
    }

    private String gerarCodigo() {
        int codigo = new Random().nextInt(900_000) + 100_000; // sempre 6 dígitos
        return String.valueOf(codigo);
    }


    public boolean validarCodigo(String codigoDigitado) {
        return codigoGerado != null
                && usuarioAlvo != null
                && codigoGerado.equals(codigoDigitado);
    }

    public boolean redefinirSenha(String novaSenha) {
        if (usuarioAlvo == null) {
            return false;
        }
        usuarioAlvo.setSenha(novaSenha);
        encerrarFluxo();
        return true;
    }

    /**
     * Limpa o estado do serviço — chamado após concluir ou cancelar.
     */
    public void encerrarFluxo() {
        this.usuarioAlvo = null;
        this.codigoGerado = null;
    }

    public Usuario getUsuarioAlvo() {
        return usuarioAlvo;
    }
}
