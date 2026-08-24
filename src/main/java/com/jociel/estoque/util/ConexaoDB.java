package com.jociel.estoque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String URL_DB = "jdbc:mysql://localhost:3307/stock_desktop";
    private static final String USUARIO_DB = "root";
    private static final String SENHA_DB = "senac";

    private static Connection conexao;

    private  ConexaoDB(){}

    public static  Connection abrirConexao() throws SQLException {
        if( conexao == null || conexao.isClosed()){
            conexao = DriverManager.getConnection(URL_DB, USUARIO_DB, SENHA_DB);
        }
        return conexao;
    }

    public static void fecharConexao() throws SQLException {
        if( conexao != null && !conexao.isClosed()){
            conexao.close();
        }
    }





}
