package com.jociel.estoque.controller;

import com.jociel.estoque.service.RecuperacaoSenhaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class NovaSenhaController {

    @FXML
    private PasswordField txtNovaSenha;
    @FXML
    private PasswordField txtConfirmarSenha;
    @FXML
    private Label lblMensagem;

    private RecuperacaoSenhaService service;

    public void initData(RecuperacaoSenhaService service) {
        this.service = service;
    }

    @FXML
    private void onRedefinir() {
        String novaSenha = txtNovaSenha.getText();
        String confirmarSenha = txtConfirmarSenha.getText();

        if (novaSenha.isBlank()) {
            lblMensagem.setText("A senha não pode ficar em branco.");
            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            lblMensagem.setText("As senhas não coincidem.");
            return;
        }

        service.redefinirSenha(novaSenha);

        // Fecha o modal de nova senha
        ((Stage) txtNovaSenha.getScene().getWindow()).close();
    }
}
