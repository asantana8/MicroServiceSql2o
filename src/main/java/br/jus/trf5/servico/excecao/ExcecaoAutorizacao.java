package br.jus.trf5.servico.excecao;

public class ExcecaoAutorizacao extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExcecaoAutorizacao(String msg) {
		super(msg);
	}

	public ExcecaoAutorizacao(String msg, Throwable cause) {
		super(msg, cause);
	}
}
