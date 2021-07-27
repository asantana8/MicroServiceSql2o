package br.jus.trf5.servico.excecao;

public class ExcecaoArquivo extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExcecaoArquivo(String msg) {
		super(msg);
	}

	public ExcecaoArquivo(String msg, Throwable cause) {
		super(msg, cause);
	}

}
