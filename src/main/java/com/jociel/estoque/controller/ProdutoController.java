package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.model.Produto;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProdutoController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCategoria;

    @FXML
    private TextField campoQuantidade;

    @FXML
    private TextField campoPreco;

    @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Label tituloProduto;

    private final EstoqueDAO dadosEstoque = EstoqueDAO.getInstancia();

    private Produto produtoEmEdicao;

    public void preencherParaEdicao( Produto produto ){
        this.produtoEmEdicao = produto;
        campoNome.setText(produto.getNome());
        campoCategoria.setText(produto.getCategoria());
        campoQuantidade.setText(String.valueOf(produto.getQuantidade()));
        campoPreco.setText(String.valueOf(produto.getPreco()));
        botaoSalvar.setText("Salvar Alterações");
        botaoCancelar.setText("Cancelar Alterações");
        tituloProduto.setText("Editar Produto Cadastrado");

    }

    @FXML
    protected void salvar(ActionEvent event) throws IOException{
        String nome = campoNome.getText();
        String categoria = campoCategoria.getText();
        if( nome == null || nome.isBlank() || categoria == null || categoria.isBlank()){
            mostrarErro("Informe um nome e uma categoria válida!");
            return;
        }
        int quantidade;
        double preco;
        try {
            quantidade = Integer.parseInt(campoQuantidade.getText().trim());
            preco = Double.parseDouble(campoPreco.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex ){
            mostrarErro("Quantidade e preço precisam ser números válidos.");
            return;
        }

        if(produtoEmEdicao == null){
            Produto produto = new Produto(0, nome, categoria, quantidade, preco);
            dadosEstoque.adicionar(produto);
            mostrarSucesso(event, "Produto inserido com sucesso!");
        } else {
            produtoEmEdicao.setNome(nome);
            produtoEmEdicao.setCategoria(categoria);
            produtoEmEdicao.setQuantidade(quantidade);
            produtoEmEdicao.setPreco(preco);
            mostrarSucesso(event, "Produto editado com sucesso!");
        }
        dadosEstoque.listarProdutos().forEach(System.out::println);
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert( Alert.AlertType.WARNING, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private void mostrarSucesso(ActionEvent event, String mensagem) throws IOException {
        Alert confirmacao = new Alert(Alert.AlertType.INFORMATION, mensagem );
        confirmacao.setHeaderText(null);
        confirmacao.showAndWait();
        GerenciadorTela.getInstancia().trocarTela(event, "estoque.fxml", "Sistema de Estoque - Estoque");
    }


    @FXML
    protected  void cancelar(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");
    }

}
