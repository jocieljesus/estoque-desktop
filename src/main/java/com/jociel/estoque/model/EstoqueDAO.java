package com.jociel.estoque.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstoqueDAO {

    private  static EstoqueDAO instance;
    private final ObservableList<Produto> listaProdutos;
    private int proximoId = 1;

    public EstoqueDAO() {
        this.listaProdutos = FXCollections.observableArrayList();
        adicionar(new Produto(0, "Mouse Óptico", "Periféricos", 25, 39.90));
        adicionar(new Produto(0, "Teclado Mecânico", "Periféricos", 8, 189.90));
        adicionar(new Produto(0, "Monitor 24", "Informática", 4, 699.00));
        adicionar(new Produto(0, "Cabo HDMI 2m", "Acessórios", 40, 19.90));
        adicionar(new Produto(0, "Notebook 15", "Informática", 3, 3299.00));
    }

    public static EstoqueDAO getInstance(){
        if (instance == null){
            instance = new EstoqueDAO();
        }
        return instance;
    }

    public ObservableList<Produto> getListaProdutos(){
        return listaProdutos;
    }

    public void adicionar(Produto produto){
        produto.setId(proximoId++);
        listaProdutos.add(produto);
    }

    public void remover(Produto produto){

        listaProdutos.remove(produto);
    }

    public double calcularValorTotalEstoque(){
        return listaProdutos.stream().mapToDouble(Produto::getValorTotal).sum();
    }

    public long contarProdutosBaixoEstoque( int limite){
        return listaProdutos.stream().filter(p -> p.getQuantidade() < limite).count();
    }
}
