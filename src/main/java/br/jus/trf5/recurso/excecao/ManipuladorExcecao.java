package br.jus.trf5.recurso.excecao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.jus.trf5.servico.excecao.ExcecaoArquivo;
import br.jus.trf5.servico.excecao.ExcecaoAutorizacao;
import br.jus.trf5.servico.excecao.ExcecaoIntegridade;
import br.jus.trf5.servico.excecao.ObjetoNaoEncontrado;

@ControllerAdvice
public class ManipuladorExcecao {

	@ExceptionHandler(ObjetoNaoEncontrado.class)
	public ResponseEntity<MensagemPadrao> objectNotFound(ObjetoNaoEncontrado e, HttpServletRequest request) {

		MensagemPadrao err = new MensagemPadrao(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(ExcecaoArquivo.class)
	public ResponseEntity<MensagemPadrao> file(ExcecaoArquivo e, HttpServletRequest request) {

		MensagemPadrao err = new MensagemPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de arquivo", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(ExcecaoAutorizacao.class)
	public ResponseEntity<MensagemPadrao> authorization(ExcecaoAutorizacao e, HttpServletRequest request) {

		MensagemPadrao err = new MensagemPadrao(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(),
				"Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(ExcecaoIntegridade.class)
	public ResponseEntity<MensagemPadrao> dataIntegrity(ExcecaoIntegridade e, HttpServletRequest request) {

		MensagemPadrao err = new MensagemPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	

}
