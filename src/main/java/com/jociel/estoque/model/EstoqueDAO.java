package com.jociel.estoque.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EstoqueDAO {

    private static EstoqueDAO instancia;
    private final ObservableList<Produto> produtosList;
    private int idProduto = 1;


    private EstoqueDAO(){
        this.produtosList = FXCollections.observableArrayList();
    }


    public static EstoqueDAO getInstancia(){
        if ( instancia == null){
            instancia = new EstoqueDAO();
        }
        return instancia;
    }

    public void adicionar(Produto produto){
        produto.setId(idProduto++);
        produtosList.add(produto);
    }

    public ObservableList<Produto> listarProdutos(){
        return produtosList;
    }

    public void remover(List<Produto> listProdutos){
            produtosList.removeAll(listProdutos);
    }


    public double calcularValorTotalEstoque(){
        return produtosList.stream().mapToDouble(Produto::getValorTotal).sum();
    }

    public long calcularEstoqueBaixo(int limite){
        return  produtosList.stream().filter( p -> p.getQuantidade() < limite).count();

    }





}
