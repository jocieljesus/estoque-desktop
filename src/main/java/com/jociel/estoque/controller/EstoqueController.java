package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.model.Produto;
import com.jociel.estoque.util.GerenciadorTela;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

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

        NumberFormat moedaFormatada = NumberFormat.getCurrencyInstance(new Locale("pr", "BR"));
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
    protected void  adicionarProduto(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "produto.fxml", "Sistema de Estoque - Adicionar Produto");
    }

    @FXML
    protected  void editarProduto(ActionEvent event) throws IOException{
        Produto produtoSelecionado = (Produto) tabelaProdutos.getSelectionModel().getSelectedItem();
        if( produtoSelecionado == null){
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


    public void mostrarAlerta(String mensagem){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    @FXML
    protected void removerProduto(){
        Produto produtoSelecionado = (Produto) tabelaProdutos.getSelectionModel().getSelectedItem();
         if( produtoSelecionado == null){
             mostrarAlerta("Selecione um produto para remover !");
             return;
         }

         Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION, "Remover o produto "+ produtoSelecionado.getNome() + " do estoque? ");
         confirmacao.setHeaderText(null);
         ButtonType btnSim = new ButtonType("Sim");
         ButtonType btnNao = new ButtonType("Não");
         confirmacao.getButtonTypes().setAll(btnSim, btnNao);
         confirmacao.showAndWait().ifPresent( botao -> {
             if ( botao == btnSim){
                 dadosEstoque.remover(produtoSelecionado);
             }
         });
    }


    @FXML
    protected void  aoVoltarMenu(ActionEvent event) throws IOException {
        GerenciadorTela.getInstancia().trocarTela(event, "menu.fxml", "Sistema de Estoque - Menu");
    }

}
