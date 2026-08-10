package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class RelatorioController {


    @FXML
    private Label labelTotalProdutos;
    @FXML
    private Label labelValorTotal;
    @FXML
    private Label labelBaixoEstoque;

    private final EstoqueDAO estoqueDAO = new EstoqueDAO();

    public RelatorioController() throws SQLException {
    }

    @FXML
    public void initialize() {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        int totalProdutos = estoqueDAO.getListaProdutos().size();
        double valorTotal = estoqueDAO.calcularValorTotalEstoque();
        long baixoEstoque = estoqueDAO.contarProdutosBaixoEstoque(10);

        labelTotalProdutos.setText(String.valueOf(totalProdutos));
        labelValorTotal.setText(formatoMoeda.format(valorTotal));
        labelBaixoEstoque.setText(String.valueOf(baixoEstoque));
    }


    @FXML
    public void voltarParaMenu(ActionEvent event) throws IOException {
        SceneManager.getInstance().trocarTela(event, "/com/jociel/estoque/menu.fxml", "Sistema de Estoque - Menu");
    }
}
