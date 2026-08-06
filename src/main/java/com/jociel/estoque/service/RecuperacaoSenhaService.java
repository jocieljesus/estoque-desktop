package com.jociel.estoque.service;

import com.jociel.estoque.model.Usuario;
import com.jociel.estoque.model.UsuarioDAO;

import java.util.Optional;
import java.util.Random;

public class RecuperacaoSenhaService {

    private Usuario usuarioAlvo;

    private  String codigoGerado;

    public String solicitarRecuperacao(String email, UsuarioDAO baseUsuario){

        Optional<Usuario> usuarioEncontrado = baseUsuario.buscarPorEmail(email);
        if( usuarioEncontrado.isEmpty()) {
            return  null;
        }

        this.codigoGerado =  gerarCodigo();
        this.usuarioAlvo = usuarioEncontrado.get();

        return this.codigoGerado;
    }

    private String gerarCodigo() {
        int codigo = new Random().nextInt(900_000)+100_00;
        return String.valueOf(codigo);
    }

    public boolean validarCodigo( String codigoDigitado){
        return  codigoGerado != null &&  usuarioAlvo != null && codigoGerado.equals(codigoDigitado);
    }

    public boolean redefinirSenha( String novaSenha){
        if( usuarioAlvo == null){
            return false;
        }
        usuarioAlvo.setSenha(novaSenha);
        encerrarFluxo();
        return true;
    }

    public void encerrarFluxo(){
        this.usuarioAlvo = null;
        this.codigoGerado = null;
    }

    public  Usuario getUsuarioAlvo(){
        return usuarioAlvo;
    }

}
