package com.jociel.estoque.model;

import com.jociel.estoque.util.ConexaoDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    private  ObservableList<Produto> produtosList;

    public EstoqueDAO(){
        this.produtosList = FXCollections.observableArrayList();
    }

    public void adicionar(Produto produto){
       String sqlInsert = "INSERT INTO produto(nome, categoria, quantidade, preco) VALUES(?,?,?,?)";

       try(Connection con = ConexaoDB.abrirConexao(); PreparedStatement pstm = con.prepareStatement(sqlInsert)){
           pstm.setString(1, produto.getNome());
           pstm.setString(2, produto.getCategoria());
           pstm.setInt(3, produto.getQuantidade());
           pstm.setDouble(4, produto.getPreco());
           pstm.execute();
       } catch (SQLException ex){
           System.err.println("[ BANCO DE DADOS ] Erro na conexão do Banco de Dados!" +   ex.getMessage());
           ex.printStackTrace();
       }
    }


    public List<Produto> listarProdutos() throws SQLException {
        List<Produto> listaInterna = new ArrayList<>();
        String sqlSelect  = "SELECT * FROM produto";
        try(
                Connection con = ConexaoDB.abrirConexao();
                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sqlSelect)
                ){
            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setPreco(rs.getDouble("preco"));
                listaInterna.add(produto);
            }
        } catch( SQLException ex){
            System.err.println("[ BANCO DE DADOS ] Erro ao executar select de produtos! " + ex.getMessage());
            ex.printStackTrace();
        }
        ConexaoDB.fecharConexao();
        return listaInterna;
    }

    public void remover(List<Produto> listProdutos){
        String sqlDelete = "DELETE FROM produto WHERE id = ?";
        try ( Connection con = ConexaoDB.abrirConexao(); PreparedStatement pstm = con.prepareStatement(sqlDelete)){

            for ( var produto : listProdutos){
                pstm.setInt(1, produto.getId());
                pstm.addBatch();
            }
            pstm.executeBatch();
        }catch (SQLException ex){
            System.err.println(" [BANCO DE DADOS] Erro ao deletar um produto! " + ex.getMessage() );
            ex.printStackTrace();

        }
        produtosList.removeAll(listProdutos);
    }

    public void atualizarProduto(Produto produto){
        String sqlUpdate = "UPDATE produto SET nome=?, categoria=?, quantidade=?, preco=? WHERE id=?";

        try(Connection con = ConexaoDB.abrirConexao(); PreparedStatement pstm = con.prepareStatement(sqlUpdate)){
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getCategoria());
            pstm.setInt(3, produto.getQuantidade());
            pstm.setDouble(4, produto.getPreco());
            pstm.setInt(5, produto.getId());
            pstm.executeUpdate();
        }catch (SQLException ex){
            System.err.println("[ BANCO DE DADOS ] Erro ao tentar atualizar um produto! "+ ex.getMessage());
            ex.printStackTrace();
        }
    }


    public double calcularValorTotalEstoque() throws SQLException {
        return listarProdutos().stream().mapToDouble(Produto::getValorTotal).sum();
    }

    public long calcularEstoqueBaixo(int limite) throws SQLException {
        return  listarProdutos().stream().filter( p -> p.getQuantidade() < limite).count();

    }
}
