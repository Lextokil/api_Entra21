package br.com.proway.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.proway.api.data.ConexaoJDBC;
import br.com.proway.api.data.ConexaoMysqlJDBC;

import br.com.proway.api.model.Usuario;


public class UsuarioDAO {
	private final ConexaoJDBC conexao;
	
	public UsuarioDAO() throws SQLException, ClassNotFoundException {
		this.conexao = new ConexaoMysqlJDBC();
	}
	
	public Long inserir(Usuario usuario) throws SQLException, ClassNotFoundException {
		Long id = null;
		String sqlQuery = "INSERT INTO usuario (nome, idade) VALUES (?, ?) ";

		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setString(1, usuario.getNome());
			stmt.setInt(2, usuario.getIdade());
			stmt.execute();
			
			this.conexao.commit();
		} catch (SQLException e) {
			this.conexao.rollback();
			throw e;
		}

		return id;
	}
	
	public int alterar(Usuario usuario) throws SQLException, ClassNotFoundException {
		String sqlQuery = "UPDATE usuario SET nome = ?, idade = ? WHERE id = ?";
		int linhasAfetadas = 0;

		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setString(1, usuario.getNome());
			stmt.setInt(2, usuario.getIdade());
			stmt.setLong(3, usuario.getId());

			linhasAfetadas = stmt.executeUpdate();
			this.conexao.commit();
		} catch (SQLException e) {
			this.conexao.rollback();
			throw e;
		}

		return linhasAfetadas;
	}
	
	public int excluir(long id) throws SQLException, ClassNotFoundException {
		int linhasAlfetadas = 0;
		String sqlQuery = "DELETE FROM usuario WHERE id = ?";

		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setLong(1, id);
			linhasAlfetadas = stmt.executeUpdate();
			this.conexao.commit();
		} catch (SQLException e) {
			this.conexao.rollback();
			throw e;
		}

		return linhasAlfetadas;
	}
	
	public Usuario selecionar(long id) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM usuario WHERE id = ?";

		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return parser(rs);
			}
		} catch (SQLException e) {
			throw e;
		}

		return null;
	}
	
	public List<Usuario> listar() throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM usuario ORDER BY id";

		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			ResultSet rs = stmt.executeQuery();

			List<Usuario> usuario = new ArrayList<>();

			while (rs.next()) {
				usuario.add(parser(rs));
			}

			return usuario;
		} catch (SQLException e) {
			throw e;
		}
	}

	private Usuario parser(ResultSet resultSet) throws SQLException {
		Usuario u = new Usuario();

		u.setId(resultSet.getLong("id"));
		u.setNome(resultSet.getString("nome"));
		u.setIdade(resultSet.getInt("idade"));
		

		return u;
	}
}

