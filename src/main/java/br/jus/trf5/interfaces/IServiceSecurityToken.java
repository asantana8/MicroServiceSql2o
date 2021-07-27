package br.jus.trf5.interfaces;

import java.net.UnknownHostException;
import java.sql.SQLException;

import br.jus.trf5.dominio.dto.LoginDTO;
import br.jus.trf5.servico.excecao.ExcecaoAutorizacao;

public interface IServiceSecurityToken<T> {
	
	public T loginComTokenPorDTO(LoginDTO loginDto) throws SQLException, UnknownHostException;

	public T buscaPorToken(String token) throws ExcecaoAutorizacao, SQLException;	
	
}
