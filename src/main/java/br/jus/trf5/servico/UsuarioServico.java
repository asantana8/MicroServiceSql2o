package br.jus.trf5.servico;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.jus.trf5.dominio.Usuario;
import br.jus.trf5.dominio.dto.LoginDTO;
import br.jus.trf5.interfaces.IServiceSecurityToken;
import br.jus.trf5.repositorio.RepositorioBase;
import br.jus.trf5.repositorio.UsuarioRepositorio;
import br.jus.trf5.seguranca.JwtUtil;
import br.jus.trf5.servico.excecao.ExcecaoAutorizacao;

@Service
@Configurable
public class UsuarioServico implements IServiceSecurityToken<Usuario> {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	RepositorioBase repositorio;

	@Autowired
	LoginDTO dto;

	@Bean
	public BCryptPasswordEncoder codificadorDeSenha() {
		return new BCryptPasswordEncoder();
	}

	public Boolean estaLogado(String token) throws NumberFormatException, SQLException, UnknownHostException {
		String login = jwtUtil.retLoginFromToken(token);
		String userlogin = login.substring(0, login.indexOf("_"));
		String userSecao = login.substring(login.indexOf("_") + 1, login.length());
		UsuarioRepositorio usuarioRepositorio = repositorio.getUsuarioRepositorio(Integer.parseInt(userSecao));
		String tokenBase = usuarioRepositorio.obterToken(userlogin, Integer.parseInt(userSecao));
		return tokenBase.equals(token);
	}

	@Override
	public Usuario buscaPorToken(String token) throws ExcecaoAutorizacao, SQLException {
		UsuarioRepositorio usuarioRepositorio;
		try {
			int valueSecao = jwtUtil.getSecaoToken(token);
			usuarioRepositorio = repositorio.getUsuarioRepositorio(valueSecao);
			Usuario usu = usuarioRepositorio.buscaPorLogin(jwtUtil.getLogin(token));
			if (usu != null) {
				return new Usuario(usu.getcodbase(), usu.getLogin(), usu.getToken(), 
						usu.getUltima_atualizacao_token());
			}

			throw new ExcecaoAutorizacao("Token inválido ou JWT Expired!");

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Usuario loginComTokenPorDTO(LoginDTO loginDto) throws SQLException, UnknownHostException {
		int codbase = loginDto.getCodBase();
		UsuarioRepositorio usuarioRepositorio = repositorio.getUsuarioRepositorio(codbase);
		Usuario usuario = usuarioRepositorio.buscaPorLogin(loginDto.getLogin());
		Boolean valido = (usuario != null) ? codificadorDeSenha().matches(loginDto.getSenha(), usuario.getSenha())
				: false;
		if (valido) {
			String token = jwtUtil.gerarToken(usuario.getLogin() + "_" + usuario.getcodbase());
			usuario.setToken(token);
			usuarioRepositorio.salvarOrAtualizar(usuario);
			return new Usuario(usuario.getcodbase(), usuario.getLogin(), usuario.getToken(),
					usuario.getUltima_atualizacao_token());
		}
		;

		throw new ExcecaoAutorizacao("Usuário/Login/Senha inválido!");
	}

}
