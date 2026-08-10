package com.jociel.estoque.controller;

import com.jociel.estoque.model.Usuario;
import com.jociel.estoque.model.UsuarioDAO;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
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

    private final UsuarioDAO bdUsuario =  UsuarioDAO.getInstancia();

    @FXML
    protected void aoApertarBotao(ActionEvent event) throws IOException {

        String usuarioDigitado = usuario.getText().toLowerCase();
        String senhaDigitada = senha.getText();

        Optional<Usuario> usuarioEncontrado = bdUsuario.buscarPorEmail(usuarioDigitado);

        if ( usuarioEncontrado.isPresent() && usuarioEncontrado.get().getSenha().equals(senhaDigitada)){

            GerenciadorTela.getInstancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");

        } else {
            erroDados.setVisible(true);
        }
    }


    @FXML
    protected void aoCadastrar(MouseEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "cadastro.fxml", "Sistema de Estoque - Cadastro de Usuário");
    }


    @FXML
    protected  void aoEsquecerSenha() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jociel/estoque/enviaEmail.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sistema de Estoque - Esqueceu a senha");
        stage.show();



    }



}
