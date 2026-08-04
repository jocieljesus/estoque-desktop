package com.jociel.estoque.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class GerenciadorTela {

    private static GerenciadorTela intancia;

    private GerenciadorTela(){
    }

    public static GerenciadorTela getInstancia(){
        if ( intancia == null){
            intancia = new GerenciadorTela();
        }
        return intancia;
    }


    public void trocarTela(ActionEvent event, String telaFXML, String titulo) throws IOException {

        FXMLLoader  fxmlLoader = new FXMLLoader(getClass().getResource("/com/jociel/estoque/"+telaFXML));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    public <T> T telaEdicao(ActionEvent event, String telaFXML, String titulo, Consumer<T> abrirEdicao) throws IOException {
        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/com/jociel/estoque/"+telaFXML));
        Parent novoRoot = fxmlLoader.load();
        T controller = fxmlLoader.getController();
        if( abrirEdicao != null){
            abrirEdicao.accept(controller);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(novoRoot);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
        return  controller;

    }




}
