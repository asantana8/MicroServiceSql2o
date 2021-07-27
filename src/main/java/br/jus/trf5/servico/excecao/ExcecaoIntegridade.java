package br.jus.trf5.servico.excecao;

public class ExcecaoIntegridade extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExcecaoIntegridade(String msg) {
		super(msg);
	}

	public ExcecaoIntegridade(String msg, Throwable cause) {
		super(msg, cause);
	}
}
