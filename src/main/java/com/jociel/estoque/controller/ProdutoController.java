package com.jociel.estoque.controller;

import com.jociel.estoque.model.EstoqueDAO;
import com.jociel.estoque.model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ProdutoController{
    @FXML private TextField campoNome;
    @FXML private TextField campoCategoria;
    @FXML private TextField campoQuantidade;
    @FXML private TextField campoPreco;
    @FXML private Button botaoSalvar;

    private final EstoqueDAO dao = EstoqueDAO.getInstance();

    // Se for null, estamos ADICIONANDO. Se tiver valor, estamos EDITANDO.
    private Produto produtoEmEdicao;

    /**
     * Chamado pela tela de Estoque antes de exibir o modal, quando o
     * usuário clica em "Editar". Preenche os campos com os dados atuais.
     */
    public void preencherParaEdicao(Produto produto) {
        this.produtoEmEdicao = produto;
        campoNome.setText(produto.getNome());
        campoCategoria.setText(produto.getCategoria());
        campoQuantidade.setText(String.valueOf(produto.getQuantidade()));
        campoPreco.setText(String.valueOf(produto.getPreco()));
        botaoSalvar.setText("Salvar Alterações");
    }

    @FXML
    private void salvar() {
        String nome = campoNome.getText();
        String categoria = campoCategoria.getText();

        if (nome == null || nome.isEmpty()) {
            mostrarErro("Informe o nome do produto.");
            return;
        }

        int quantidade;
        double preco;
        try {
            quantidade = Integer.parseInt(campoQuantidade.getText().trim());
            preco = Double.parseDouble(campoPreco.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            mostrarErro("Quantidade e preço precisam ser números válidos.");
            return;
        }

        if (produtoEmEdicao == null) {
            // Modo adicionar
            dao.adicionar(new Produto(0, nome, categoria, quantidade, preco));
        } else {
            // Modo edição: atualiza o objeto que já está na lista do DAO
            produtoEmEdicao.setNome(nome);
            produtoEmEdicao.setCategoria(categoria);
            produtoEmEdicao.setQuantidade(quantidade);
            produtoEmEdicao.setPreco(preco);
        }

        fecharJanela();
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        // Pega a própria janela (Stage) a partir de um componente da tela
        // e a fecha. É assim que se fecha uma janela modal a partir de dentro dela.
        Stage janelaAtual = (Stage) botaoSalvar.getScene().getWindow();
        janelaAtual.close();
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
