package com.jociel.estoque.controller;

import com.jociel.estoque.model.UsuarioDAO;
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

public class RecuperarSenhaController {

    @FXML
    private TextField txtUsuario;
    @FXML
    private Label lblMensagem;

    private final RecuperacaoSenhaService service = new RecuperacaoSenhaService();
    private final UsuarioDAO baseUsuarios = UsuarioDAO.getInstance();


    @FXML
    private void onEnviarCodigo() {
        String usuario = txtUsuario.getText().trim();

        String codigo = service.solicitarRecuperacao(usuario, baseUsuarios);

        if (codigo == null) {
            lblMensagem.setText("Usuário não encontrado.");
            return;
        }
        ((Stage) txtUsuario.getScene().getWindow()).close();
        abrirModalConfirmacao(codigo);
    }

    private void abrirModalConfirmacao(String codigoGerado) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/jociel/estoque/confirmar-codigo.fxml"));
                Parent root = loader.load();

                ConfirmarCodigoController controller = loader.getController();
                controller.initData(service, codigoGerado);

                Stage modal = new Stage();
                modal.setTitle("Confirmar código");
                modal.initModality(Modality.WINDOW_MODAL);
                modal.initOwner(txtUsuario.getScene().getWindow());
                modal.setScene(new Scene(root));

                modal.showAndWait();

                lblMensagem.setStyle("-fx-text-fill: green;");
                lblMensagem.setText("Se a senha foi redefinida, você já pode fazer login.");

                ((Stage) txtUsuario.getScene().getWindow()).close();


            } catch (IOException e) {
                lblMensagem.setText("Erro ao abrir tela de confirmação.");
                e.printStackTrace();
            }
        });
    }
}
