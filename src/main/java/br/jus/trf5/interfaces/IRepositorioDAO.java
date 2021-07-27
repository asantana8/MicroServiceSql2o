package br.jus.trf5.interfaces;

import java.util.List;

public interface IRepositorioDAO<T> {
	public T buscaPorChave(String value);
	
	public T buscaPorChave(int value);

	public List<T> lista();

	public List<T> buscaPorNome(String value);

	public void salvarOrAtualizar(T bean);

	public void deletar(int id);
	
}
