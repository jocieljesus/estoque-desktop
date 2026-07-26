package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.model.Produto;
import com.jociel.estoque.util.SceneManager;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EstoqueController {

    @FXML
    private TableView<Produto> tabelaProdutos;
    @FXML
    private TableColumn<Produto, Integer> colunaId;
    @FXML
    private TableColumn<Produto, String> colunaNome;
    @FXML
    private TableColumn<Produto, String> colunaCategoria;
    @FXML
    private TableColumn<Produto, Integer> colunaQuantidade;
    @FXML
    private TableColumn<Produto, Double> colunaPreco;
    @FXML
    private TextField campoBusca;

    private final EstoqueDAO estoqueDao =  EstoqueDAO.getInstance();
    private FilteredList<Produto> listaFiltrada;

    @FXML
    public void initialize(){
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listaFiltrada =  new FilteredList<>(estoqueDao.getListaProdutos(), p -> true);
        tabelaProdutos.setItems(listaFiltrada);

        campoBusca.textProperty().addListener((obs, textoAntigo, textoNovo) -> {
            String filtro = textoNovo == null ? "" : textoNovo.toLowerCase();
            listaFiltrada.setPredicate(produto -> filtro.isEmpty() || produto.getNome().toLowerCase().contains(filtro) || produto.getCategoria().toLowerCase().contains(filtro));
        });
    }

    @FXML
    protected void adicionarProduto()  {
        SceneManager.getInstance().abrirModal(
                "/com/jociel/estoque/produto.fxml",
                "Adicionar Produto"
        );
    }




    @FXML
    protected void editarProduto(){
        Produto produtoSelecionado =  tabelaProdutos.getSelectionModel().getSelectedItem();
        if(  produtoSelecionado == null){
            mostrarAlerta("Selecione um produto para editar.");
            return;
        }
        SceneManager.getInstance().abrirModal(
                "/com/jociel/estoque/produto.fxml",
                "Adicionar Produto",
                (ProdutoController controller) -> controller.preencherParaEdicao(produtoSelecionado)
        );
    }

    private void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    @FXML
    protected void removerProduto(){
        Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Selecione um produto para remover.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
                "Remover o produto \"" + selecionado.getNome() + "\" do estoque?");
        confirmacao.setHeaderText(null);
        confirmacao.showAndWait().ifPresent(botao -> {
            if (botao == ButtonType.OK) {
                estoqueDao.remover(selecionado);
            }
        });
    }


    @FXML
    protected void voltarParaMenu(ActionEvent event) throws IOException {
        SceneManager.getInstance().voltarTela(
                event,
                "/com/jociel/estoque/menu.fxml",
                "Sistema de Estoque - Menu"
        );
    }



}
