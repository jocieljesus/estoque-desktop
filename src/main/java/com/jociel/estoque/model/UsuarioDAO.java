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

    public UsuarioDAO() {
        bdUsuarios = new HashSet<>();
    }


    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection con = ConexaoDB.getConexao();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, email);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return Optional.empty();
    }

    public void cadastrarUsuario(Usuario usuario) {

        String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

        String sql = "INSERT INTO usuario (email, senha) VALUES (?,?)";
        try (Connection con = ConexaoDB.getConexao();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, senhaCriptografada);
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizaSenha(String email, String novaSenha) {
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";
        try (Connection con = ConexaoDB.getConexao();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, novaSenha);
            pstm.setString(2, email);
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean autenticar(String emailDigitado, String senhaDigitada) {
        Optional<Usuario> usuarioOpt = buscarPorEmail(emailDigitado);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();

        return BCrypt.checkpw(senhaDigitada, usuario.getSenha());
    }


}


