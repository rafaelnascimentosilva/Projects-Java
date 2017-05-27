package br.com.contato.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.contato.modelo.Contato;

public class ContatoDAO {
	private Connection connection;

	public ContatoDAO(Connection connection) {
		this.connection = connection;
	}

	public void inserir(Contato contato) {
		String sql = "insert into tbcontato(nome,fone,nascimento)values(?,?,?)";
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getFone());
			statement.setDate(3, new Date(contato.getNascimento().getTimeInMillis()));
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			System.out.println("erro ao conectar ao banco dao!!!");
			throw new RuntimeException(e);
		}
	}

	public List<Contato> getLista() {
		try {
			PreparedStatement statement = this.connection.prepareStatement("select * from tbcontato");

			ResultSet rs = statement.executeQuery();
			List<Contato> listaDeContatos = new ArrayList<Contato>();

			while (rs.next()) {
				Contato contato = new Contato();
				contato.setId(new Integer(rs.getString("id")));
				contato.setNome(rs.getString("nome"));
				contato.setFone(rs.getString("fone"));
				Calendar nascimento = Calendar.getInstance();
				nascimento.setTime(rs.getDate("nascimento"));
				contato.setNascimento(nascimento);
				listaDeContatos.add(contato);
			}

			rs.close();
			statement.close();
			return listaDeContatos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deletar(Contato contato) {

		String sql = "delete from tbcontato where id=?";
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
			statement.setLong(1, contato.getId());
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void alterar(Contato contato) {
		String sql = "update tbcontato set nome=?, fone=?, nascimento=? where id=?";
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getFone());
			statement.setDate(3, new Date(contato.getNascimento().getTimeInMillis()));
			statement.setLong(4, contato.getId());
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}