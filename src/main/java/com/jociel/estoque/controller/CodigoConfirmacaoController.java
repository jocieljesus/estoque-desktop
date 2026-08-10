package com.jociel.estoque.controller;

import com.jociel.estoque.service.RecuperacaoSenhaService;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CodigoConfirmacaoController {

    @FXML
    private Label codigoRecuperacao;

    @FXML
    private TextField codigoInformado;

    @FXML
    private Label codigoInvalido;

    private  RecuperacaoSenhaService service;

    public void initData( RecuperacaoSenhaService service, String codigoGerado){
        this.service = service;
        codigoRecuperacao.setText(codigoGerado
        );

    }

    @FXML
    protected  void aoValidarCodigo(ActionEvent event) throws IOException {
        String codigo = codigoInformado.getText();
        if(!service.validarCodigo(codigo)){
            codigoInvalido.setVisible(true);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jociel/estoque/codigoConfirmacao.fxml"));
        Parent root = fxmlLoader.load();

        NovaSenhaController controller = fxmlLoader.getController();
        controller.initData(service);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Código");
        stage.setScene(scene);
        stage.show();

    }
}
