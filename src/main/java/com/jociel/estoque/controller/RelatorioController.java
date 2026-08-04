package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class RelatorioController {

    @FXML
    private Label lblTotalProdutos;

    @FXML
    private Label lblValorTotal;

    @FXML
    private Label lblEstoqueBaixo;

    private final EstoqueDAO dadosEstoque = EstoqueDAO.getInstancia();

    @FXML
    public  void initialize(){

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        int totalProdutos = dadosEstoque.listarProdutos().size();
        lblTotalProdutos.setText(String.valueOf(totalProdutos));

        double valorTotalEstoque = dadosEstoque.calcularValorTotalEstoque();
        lblValorTotal.setText(formatoMoeda.format(valorTotalEstoque));

        long estoqueBaixo = dadosEstoque.calcularEstoqueBaixo(10);
        lblEstoqueBaixo.setText(String.valueOf(estoqueBaixo));


        // Conversao de tipos
        // Inteiro ou Double  para String ->  String.valueOf( 1 ) saída -> "1"
        // String ou Double ara inteiro -> Integer.parseInt( "1" ) saída -> 1
        // Inteiro ou String para double ->  Double.parseDouble( "8.5" ) -> saída 8.5
        // String para boolean ->  Boolean.parseBoolean( "true" ) -> saída true


    }






    @FXML
    protected void aoVoltarAoMenu(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");
    }

}
