package com.jociel.estoque.controller;

import com.jociel.estoque.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usuario;

    @FXML
    private PasswordField senha;

    @FXML
    private TextFlow erroDados;

    private final String usuarioCadastrado = "maria@gmail.com";
    private final String senhaCadastrada = "010203";

    @FXML
    protected void aoApertarBotao(ActionEvent event) throws IOException {

        if (usuarioCadastrado.equalsIgnoreCase(usuario.getText()) && senhaCadastrada.equals(senha.getText())) {
            SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/menu.fxml", "Sistema de Estoque - Menu");
        } else {
            erroDados.setVisible(true);
        }
    }

    @FXML
    protected void aoEsquecerSenha() {
        System.out.println(" Você esqueceu! Já não é problema meu.");
    }

}
