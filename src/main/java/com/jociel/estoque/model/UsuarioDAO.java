package com.jociel.estoque.model;

import com.jociel.estoque.util.ConexaoDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UsuarioDAO {

    private Set<Usuario> bdUsuarios;

    public UsuarioDAO(){
        bdUsuarios = new HashSet<>();
    }

    public boolean validarLogin(String email, String senha){

        Optional<Usuario> usuarioEncontrado = buscarPorEmail(email);

        if( usuarioEncontrado.isEmpty()){
            return false;
        }

        return BCrypt.checkpw(senha, usuarioEncontrado.get().getSenha());
    }

    public Optional<Usuario> buscarPorEmail(String email){
       String sqlSelect = "SELECT * FROM usuario WHERE email = ? ";

       try ( Connection con = ConexaoDB.abrirConexao(); PreparedStatement pstm = con.prepareStatement(sqlSelect)){
           pstm.setString(1, email);
           try (ResultSet rs = pstm.executeQuery()){
               if(rs.next()){
                   Usuario usuario = new Usuario();
                   usuario.setId(rs.getInt("id"));
                   usuario.setEmail(rs.getString("email"));
                   usuario.setSenha(rs.getString("senha"));
                   return Optional.of(usuario);
               }
           }
       } catch (SQLException ex){
           System.err.println("[BANCO DE DADOS] ERRO ao buscar usuario por email! " + ex.getMessage());
           ex.printStackTrace();
       }
        return  Optional.empty();
    }

    public void cadastrarUsuario(Usuario usuario){
        String sqlInsert = "INSERT INTO usuario (email, senha) VALUES (?,?)";

        String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

        try(Connection con = ConexaoDB.abrirConexao(); PreparedStatement pstm = con.prepareStatement(sqlInsert)){
            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, senhaCriptografada);
            pstm.execute();

        } catch (SQLException ex){
            System.err.println("[BANCO DE DADOS] ERRO ao inserir novo usuário! " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void atualizarSenha(int id, String novaSenha){
        String sqlUpdate = "UPDATE usuario SET senha=? WHERE id=?";

        String senhaCriptografada = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        try( Connection con = ConexaoDB.abrirConexao(); PreparedStatement pstm = con.prepareStatement(sqlUpdate)){
            pstm.setString(1, senhaCriptografada);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException ex){
            System.err.println("[BANCO DE DADOS] ERRO ao atualizar senha! " + ex.getMessage());
            ex.printStackTrace();
        }
    }


}


