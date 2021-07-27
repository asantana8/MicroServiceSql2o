package br.jus.trf5.recurso.excecao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "timestamp", "status", "error", "exception", "message", "path" })
public class Mensagem {

	private Long timestamp;
	private Integer status;
	private String error;
	private String exception;
	private String message;
	private String path;

	public Mensagem(Long timestamp, Integer status, String error, String exception, String message, String path) {
		super();
		this.timestamp = System.currentTimeMillis();
		this.status = status;
		this.error = error;
		this.exception = exception;
		this.message = message;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
