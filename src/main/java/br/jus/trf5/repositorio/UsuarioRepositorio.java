package br.jus.trf5.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.UnexpectedRollbackException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import br.jus.trf5.dominio.Usuario;
import br.jus.trf5.interfaces.IRepositorioDAO;

@Repository
public class UsuarioRepositorio implements IRepositorioDAO<Usuario> {

	public Sql2o connection;

	public UsuarioRepositorio() {
	}

	public UsuarioRepositorio(Sql2o connection) {
		this.connection = connection;
	}

	public Sql2o getConnection() {
		return connection;
	}

	public void setConnection(Sql2o connection) {
		this.connection = connection;
	}

	public String obterToken(String login, Integer codbase) {
		try (Connection con = connection.open()) {
			String scriptUsuario = " SELECT u.token FROM Usuario u WHERE u.login = :login";
			return con.createQuery(scriptUsuario).addParameter("login", login).executeAndFetchFirst(String.class);
		}
	}

	public Usuario buscaPorLogin(String login) {
		try (Connection con = connection.open()) {
			String scriptUsuario = "SELECT u.login, u.codbase, u.senha, u.ultima_atualizacao_token, u.token  FROM Usuario u WHERE u.login = :login";
			return con.createQuery(scriptUsuario).addParameter("login", login).executeAndFetchFirst(Usuario.class);
		} catch (Exception e) {
			throw new UnexpectedRollbackException(e.getCause().getCause().getMessage(), e);
		}
	}

	public void atualizarToken(String login, int codbase, String token) {
		try (Connection con = connection.beginTransaction()) {
			String scriptUsuario = "UPDATE Usuario SET Token = :token, Ultima_Atualizacao_Token = SYSDATE\n WHERE Login = :login";
			con.createQuery(scriptUsuario).addParameter("token", token).addParameter("login", login)
					.addParameter("codbase", codbase).executeUpdate();
			con.commit();
		}
	}

	@Override
	public void salvarOrAtualizar(Usuario bean) {
		String scriptUsuario = "UPDATE Usuario SET Token = :token, Ultima_Atualizacao_Token = SYSDATE\n WHERE Login = :login AND codbase = :codbase";
		try (Connection con = connection.beginTransaction()) {
			Integer result = con.createQuery(scriptUsuario).addParameter("token", bean.getToken())
					.addParameter("login", bean.getLogin()).addParameter("codbase", bean.getcodbase()).executeUpdate()
					.getResult();

			if (result == 0) {
				scriptUsuario = "INSERT INTO Usuario (login, token, senha, codbase, Ultima_Atualizacao_Token) values (:login, :token, :senha, :codbase, SYSDATE)";
				con.createQuery(scriptUsuario).addParameter("login", bean.getLogin())
						.addParameter("token", bean.getToken()).addParameter("senha", bean.getSenha())
						.addParameter("codbase", bean.getcodbase()).executeUpdate();
			}
			con.commit();
		}

	}

	@Override
	public List<Usuario> lista() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> buscaPorNome(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario buscaPorChave(int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscaPorChave(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
