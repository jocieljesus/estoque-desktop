package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.model.Produto;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

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

    private final EstoqueDAO dadosEstoque =  EstoqueDAO.getInstancia();
    private FilteredList<Produto> listaFiltrada;

    @FXML
    public void initialize(){
        colunaId.setCellValueFactory( new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        colunaCategoria.setCellValueFactory( new PropertyValueFactory<>("categoria"));
        colunaQuantidade.setCellValueFactory( new PropertyValueFactory<>("quantidade"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listaFiltrada = new FilteredList<>( dadosEstoque.listarProdutos(), p -> true);
        tabelaProdutos.setItems(listaFiltrada);

        campoBusca.textProperty().addListener( (obs, textoAntigo, textoNovo) ->{
            String filtro = textoNovo == null ? "" : textoNovo.toLowerCase();
            listaFiltrada.setPredicate( produto -> filtro.isEmpty() || produto.getNome().toLowerCase().contains(filtro) || produto.getCategoria().toLowerCase().contains(filtro) || String.valueOf(produto.getPreco()).contains(filtro));
        } );
    }


    @FXML
    protected void  adicionarProduto(){

    }

    @FXML
    protected  void editarProduto(){

    }

    @FXML
    protected void removerProduto(){

    }


    @FXML
    protected void  aoVoltarMenu(ActionEvent event) throws IOException {
        GerenciadorTela.getIntancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");
    }

}
