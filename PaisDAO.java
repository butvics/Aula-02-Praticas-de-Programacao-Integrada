package br.usjt.OO;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

public class PaisDAO {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	public Connection obtemConexao() throws SQLException {
		return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/pais?useTimezone=true&serverTimezone=America/Sao_Paulo&user=root&password=1234");
	}
	public void incluir(int idPais, String nomePais, long populacaoPais, double areaPais) {
		String sqlInsert = "INSERT INTO pais(idPais, nomePais, populacaoPais, areaPais) VALUES (?, ?, ?, ?)";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setInt(1, idPais);
			stm.setString(2, nomePais);
			stm.setLong(3, populacaoPais);
			stm.setDouble(4, areaPais);
			stm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void atualizar(int idPais, String nomePais, long populacaoPais, double areaPais) {
		String sqlUpdate = "UPDATE pais SET nomePais=?, populacaoPais=?, areaPais=? WHERE idPais=?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setInt(1, idPais);
			stm.setString(2, nomePais);
			stm.setLong(3, populacaoPais);
			stm.setDouble(4, areaPais);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void excluir(int idPais) {
		String sqlDelete = "DELETE FROM pais WHERE idPais = ?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, idPais);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })

	public ArrayList carregar(int idPais) {
		ArrayList retorno = new ArrayList();
		String sqlSelect = "SELECT nomePais, populacaoPais, areaPais FROM pais WHERE idPais = ?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, idPais);
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					retorno.add(rs.getString("nomePais"));
					retorno.add(rs.getString("populacaoPais"));
					retorno.add(rs.getString("areaPais"));
				} else {
					retorno.add(null);
					retorno.add(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return retorno;
	}
}