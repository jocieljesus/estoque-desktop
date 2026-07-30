package com.jociel.estoque.controller;

import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Map;

public class LoginController {
    @FXML
    private TextField usuario;

    @FXML
    private PasswordField senha;

    @FXML
    private TextFlow erroDados;

    private Map<String, String> usuariosCadastrados = Map.of(
            "admin@gmail.com", "admin",
            "jociel@gmail.com", "010203",
            "funci@gmail.com", "1234"
    );

    @FXML
    protected void aoApertarBotao(ActionEvent event) throws IOException {

        String usuarioDigitado = usuario.getText().toLowerCase();
        String senhaDigitada = senha.getText();

        if ( usuariosCadastrados.containsKey(usuarioDigitado) && usuariosCadastrados.get(usuarioDigitado).equals(senhaDigitada) ){


            GerenciadorTela.getIntancia().trocarTela(event, "menu.fxml", "Sistema Estoque - Menu");

        } else {
            erroDados.setVisible(true);
        }
    }

    @FXML
    protected  void aoEsquecerSenha(){
        System.out.println(" Você esqueceu! Já não é problema meu.");
    }

}
