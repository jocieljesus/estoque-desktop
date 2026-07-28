package com.jociel.estoque.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GerenciadorTela {

    private static GerenciadorTela intancia;

    private GerenciadorTela(){
    }

    public static GerenciadorTela getIntancia(){
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




}
