package com.jociel.estoque.controller;

import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ProdutoController {


    @FXML
    protected void salvar(){

    }

    @FXML
    protected  void cancelar(ActionEvent event) throws IOException {
        GerenciadorTela.getIntancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");
    }

}
