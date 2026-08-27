package com.jociel.estoque.service;

import com.jociel.estoque.model.Usuario;
import com.jociel.estoque.model.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class RecuperacaoSenhaService {
    private Usuario usuarioAlvo;

    private  String codigoGerado;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public  RecuperacaoSenhaService(){}

    public String solicitarRecuperacao(String email){

        Optional<Usuario> usuarioEncontrado = usuarioDAO.buscarPorEmail(email);
        if( usuarioEncontrado.isEmpty()) {
            return  null;
        }

        this.codigoGerado =  gerarCodigo();
        this.usuarioAlvo = usuarioEncontrado.get();

        return this.codigoGerado;
    }

    private String gerarCodigo() {
        int codigo = new Random().nextInt(900_000)+100_000;
        return String.valueOf(codigo);
    }

    public boolean validarCodigo( String codigoDigitado){
        return  codigoGerado != null &&  usuarioAlvo != null && codigoGerado.equals(codigoDigitado);
    }

    public boolean redefinirSenha( String novaSenha){
        if( usuarioAlvo == null){
            return false;
        }
        usuarioDAO.atualizarSenha(usuarioAlvo.getId(), novaSenha);
        encerrarFluxo();
        return true;
    }

    public boolean verificarSenhaAntiga(String novaSenha){
        if( usuarioAlvo == null){
            return  false;
        }

        String senhaCriptografada = BCrypt.hashpw(novaSenha, BCrypt.gensalt());

        return  BCrypt.checkpw(senhaCriptografada, usuarioAlvo.getSenha());
    }

    public void encerrarFluxo(){
        this.usuarioAlvo = null;
        this.codigoGerado = null;
    }


}
