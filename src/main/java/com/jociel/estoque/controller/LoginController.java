package com.jociel.estoque.controller;

import com.jociel.estoque.util.SceneManager;
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
            "admin@estoque.com", "admin123",
            "maria@gmail.com", "010203",
            "jociel@gmail.com", "010101"
    );

    @FXML
    protected void aoApertarBotao(ActionEvent event) throws IOException {
        String usuarioDigitado = usuario.getText().toLowerCase();
        String senhaDigitada = senha.getText();
        if (usuariosCadastrados.containsKey(usuarioDigitado) && usuariosCadastrados.get(usuarioDigitado).equals(senhaDigitada)) {
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
