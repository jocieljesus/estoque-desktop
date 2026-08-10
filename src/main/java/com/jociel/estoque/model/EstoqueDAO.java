package com.jociel.estoque.model;

import com.jociel.estoque.util.ConexaoDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    private final ObservableList<Produto> listaProdutos;
    private int proximoId = 1;

    public EstoqueDAO() throws SQLException {
        this.listaProdutos = FXCollections.observableArrayList();
    }


    public List<Produto> getListaProdutos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection con = ConexaoDB.getConexao();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void adicionar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, categoria, quantidade, preco) VALUES (?,?,?,?)";
        try (Connection con = ConexaoDB.getConexao();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getCategoria());
            pstm.setInt(3, produto.getQuantidade());
            pstm.setDouble(4, produto.getPreco());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome=?, categoria=?,  quantidade=?, preco=? WHERE id=?";
        try (Connection con = ConexaoDB.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void remover(Produto produto) {

        String sql = "DELETE FROM produto WHERE id=?";
        try (Connection con = ConexaoDB.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, produto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calcularValorTotalEstoque() {
        return listaProdutos.stream().mapToDouble(Produto::getValorTotal).sum();
    }

    public long contarProdutosBaixoEstoque(int limite) {
        return listaProdutos.stream().filter(p -> p.getQuantidade() < limite).count();
    }
}
