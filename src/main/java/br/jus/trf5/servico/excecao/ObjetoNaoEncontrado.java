package br.jus.trf5.servico.excecao;

public class ObjetoNaoEncontrado extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjetoNaoEncontrado(String msg) {
		super(msg);
	}

	public ObjetoNaoEncontrado(String msg, Throwable cause) {
		super(msg, cause);
	}

}
