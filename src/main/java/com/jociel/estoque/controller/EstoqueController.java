package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.model.Produto;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EstoqueController {

    @FXML
    private TextField campoBusca;

    @FXML
    private TableView tabelaProdutos;

    @FXML
    private TableColumn colunaId;

    @FXML
    private TableColumn colunaNome;

    @FXML
    private TableColumn colunaCategoria;

    @FXML
    private TableColumn colunaQuantidade;

    @FXML
    private TableColumn colunaPreco;

    private final EstoqueDAO dadosEstoque = EstoqueDAO.getInstancia();
    private FilteredList<Produto> listaFiltrada;

    @FXML
    public void initialize() {
        tabelaProdutos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listaFiltrada = new FilteredList<>(dadosEstoque.listarProdutos(), p -> true);
        tabelaProdutos.setItems(listaFiltrada);

        campoBusca.textProperty().addListener((obs, textoAntigo, textoNovo) -> {
            String filtro = textoNovo == null ? "" : textoNovo.toLowerCase();
            listaFiltrada.setPredicate(produto -> filtro.isEmpty() || produto.getNome().toLowerCase().contains(filtro) || produto.getCategoria().toLowerCase().contains(filtro) || String.valueOf(produto.getPreco()).contains(filtro));
        });
    }


    @FXML
    protected void adicionarProduto(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "produto.fxml", "Sistema de Estoque - Adicionar Produto");
    }

    @FXML
    protected void editarProduto(ActionEvent event) throws IOException {
        Produto produtoSelecionado = (Produto) tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            mostrarAlerta("Selecione um produto para editar!");
            return;
        }
        GerenciadorTela.getInstancia().telaEdicao(
                event,
                "produto.fxml",
                "Sistema de Estoque - Editar Produto",
                (ProdutoController controller) -> controller.preencherParaEdicao(produtoSelecionado)
        );
    }


    public void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    @FXML
    protected void removerProduto() {
        ObservableList produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItems();
        if (produtoSelecionado.isEmpty()) {
            mostrarAlerta("Selecione um produto para remover !");
            return;
        }

        List<Produto> listaProduto = new ArrayList<>(produtoSelecionado);
        String produtosExcluidos = "";
        for (Produto p : listaProduto){
            produtosExcluidos += p.getId() +" " + p.getNome()+"\n";
        }
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION, "Remover o produto \n" + produtosExcluidos + "\ndo estoque? ");
        confirmacao.setHeaderText(null);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        confirmacao.getButtonTypes().setAll(btnSim, btnNao);
        confirmacao.showAndWait().ifPresent(botao -> {
            if (botao == btnSim) {
                dadosEstoque.remover(listaProduto);
            }
        });
    }


    @FXML
    protected void aoVoltarMenu(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");
    }

}
