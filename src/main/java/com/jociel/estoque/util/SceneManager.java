package com.jociel.estoque.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SceneManager {

    private static SceneManager instance;

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }


    public void trocarTela(ActionEvent event, String caminho, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
        Parent novoRoot = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(novoRoot);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }


    public <T> T abrirEdicao(ActionEvent event, String caminho, String titulo, Consumer<T> antesDeExibir) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent novoRoot = loader.load();
        T controller = loader.getController();
        if (antesDeExibir != null) {
            antesDeExibir.accept(controller);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(novoRoot);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
        return controller;

    }


}

