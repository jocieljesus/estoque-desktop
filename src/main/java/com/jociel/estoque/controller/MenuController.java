package com.jociel.estoque.controller;

import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {


    @FXML
    protected void  aoVisualizarEstoque(ActionEvent event) throws IOException{
        GerenciadorTela.getInstancia().trocarTela(event, "estoque.fxml", "Sistema de Estoque - Estoque");
    }

    @FXML
    protected void  aoCadastrarProduto(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "produto.fxml", "Sistema de Estoque - Produto");
    }

    @FXML
    protected  void aoGerarRelatorio(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "relatorio.fxml", "Sistema de Estoque - Relatório");
    }

    @FXML
    protected void aoSair(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "login.fxml", "Sistema de Estoque - Login");
    }

}
