package com.jociel.estoque.controller;

import com.jociel.estoque.service.RecuperacaoSenhaService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ConfirmarCodigoController {

    @FXML
    private Label lblCodigoFicticio;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Label lblMensagem;

    private RecuperacaoSenhaService service;
    private Map<String, String> baseUsuarios;

    public void initData(RecuperacaoSenhaService service, String codigoGerado) {
        this.service = service;

        lblCodigoFicticio.setText("Código enviado (simulado): " + codigoGerado);
    }

    @FXML
    private void onConfirmar() {
        String codigoDigitado = txtCodigo.getText().trim();

        if (!service.validarCodigo(codigoDigitado)) {
            lblMensagem.setText("Código inválido.");
            return;
        }
        ((Stage) txtCodigo.getScene().getWindow()).close();
        abrirModalNovaSenha();
    }

    private void abrirModalNovaSenha() {

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/jociel/estoque/nova-senha.fxml"));
                Parent root = loader.load();

                NovaSenhaController controller = loader.getController();
                controller.initData(service);

                Stage modal = new Stage();
                modal.setTitle("Nova senha");
                modal.initModality(Modality.WINDOW_MODAL);
                modal.initOwner(txtCodigo.getScene().getWindow());
                modal.setScene(new Scene(root));
                modal.showAndWait();


                ((Stage) txtCodigo.getScene().getWindow()).close();

            } catch (IOException e) {
                lblMensagem.setText("Erro ao abrir tela de nova senha.");
                e.printStackTrace();
            }
        });
    }
}
