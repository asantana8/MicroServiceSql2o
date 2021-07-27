package br.jus.trf5.dominio.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import br.jus.trf5.dominio.enums.TipoCodigoInstancia;

@Component
public class LoginDTO {

	@NotBlank(message = "Preenchimento obrigatório")
	private TipoCodigoInstancia codbase;

	@NotBlank(message = "Preenchimento obrigatório")
	private String login;

	@NotBlank(message = "Preenchimento obrigatório")
	private String senha;

	public LoginDTO() {
		super();
	}

	public LoginDTO(@NotBlank(message = "Preenchimento obrigatório") TipoCodigoInstancia codbase,
			@NotBlank(message = "Preenchimento obrigatório") String login,
			@NotBlank(message = "Preenchimento obrigatório") String senha) {
		super();
		this.codbase = codbase;
		this.login = login;
		this.senha = senha;
	}

	public int getCodBase() {
		return codbase.getId();
	}

	public void setCodBase(TipoCodigoInstancia codbase) {
		this.codbase = codbase;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
