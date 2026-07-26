package com.jociel.estoque.controller;

import com.jociel.estoque.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {


    @FXML
    protected void aoSair(ActionEvent event) throws IOException {
        SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/login.fxml", "Sistema de Estoque - Login");
    }

    @FXML
    protected void aoVizualizarEstoque(ActionEvent event) throws IOException {
        SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/estoque.fxml", "Sistema de Estoque - Estoque");


    }

    @FXML
    protected void aoAdicionarProduto(ActionEvent event) throws IOException {
        SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/produto.fxml", "Sistema de Estoque - Entrada");
    }

    @FXML
    protected void aoVerRelatorio(ActionEvent event) throws IOException {
        SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/relatorio.fxml", "Sistema de Estoque - Relatório");
    }
}
