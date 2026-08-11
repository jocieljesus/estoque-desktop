package com.jociel.estoque.controller;

import com.jociel.estoque.service.RecuperacaoSenhaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class NovaSenhaController {

    @FXML
    private PasswordField novaSenha;

    @FXML
    private PasswordField confirmaSenha;

    @FXML
    private Label senhasDiferentes;

    private RecuperacaoSenhaService service;


    public void NovaSenha( RecuperacaoSenhaService service){
        this.service = service;
    }

    @FXML
    protected  void aoConfirmarSenha(){
        String novaSenhaText =  novaSenha.getText();
        String confirmaSenhaText = confirmaSenha.getText();

        if( novaSenhaText.isBlank()){
            senhasDiferentes.setText("A nova senha não pode ficar em branco");
            senhasDiferentes.setVisible(true);
            return;
        }
        if( !novaSenhaText.equals(confirmaSenhaText)){
            senhasDiferentes.setVisible(true);
            return;
        }

        service.redefinirSenha(novaSenhaText);
        mostrarAlerta("Senha alterada com sucesso!");
        ((Stage) novaSenha.getScene().getWindow()).close();
    }

    public void mostrarAlerta( String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
