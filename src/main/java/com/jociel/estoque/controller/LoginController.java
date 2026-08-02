package com.jociel.estoque.controller;

import com.jociel.estoque.model.Usuario;
import com.jociel.estoque.model.UsuarioDAO;
import com.jociel.estoque.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    @FXML
    private TextField usuario;

    @FXML
    private PasswordField senha;

    @FXML
    private TextFlow erroDados;

    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

    @FXML
    protected void aoApertarBotao(ActionEvent event) throws IOException {
        String usuarioDigitado = usuario.getText().toLowerCase();
        String senhaDigitada = senha.getText();

        Optional<Usuario> usuarioEncontrado = usuarioDAO.buscaPorEmail(usuarioDigitado);

        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().getSenha().equals(senhaDigitada)) {
            SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/menu.fxml", "Sistema de Estoque - Menu");
        } else {
            erroDados.setVisible(true);
        }
    }

    @FXML
    protected void aoEsquecerSenha() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/jociel/estoque/recuperar-senha.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            modal.setTitle("Recuperar Senha");
            modal.initModality(Modality.WINDOW_MODAL);

            Stage janelaLogin = (Stage) usuario.getScene().getWindow();
            modal.initOwner(janelaLogin);

            modal.setScene(new Scene(root));
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
