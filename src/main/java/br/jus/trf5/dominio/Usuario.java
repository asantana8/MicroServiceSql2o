package br.jus.trf5.dominio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario implements Serializable, UserDetails {

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	@Id
	private String login;

	private String token;

	@JsonIgnore
	private String senha;

	@JsonIgnore
	private int codbase;

	@JsonIgnore
	private Date ultima_atualizacao_token;

	public Usuario() {

	}

	public Usuario(int codbase, String login, String senha) {
		this.codbase = codbase;
		this.login = login;
		this.senha = senha;
	}

	public Usuario(int codbase, String login, String token, Date ultima_atualizacao_token) {
		this.codbase = codbase;
		this.login = login;
		this.token = token;
		this.ultima_atualizacao_token = ultima_atualizacao_token;
	}

	public Usuario(int codbase, String login, String senha, String token, Date ultima_atualizacao_senha,
			Date ultima_atualizacao_token) {
		this.codbase = codbase;
		this.login = login;
		this.senha = senha;
		this.token = token;
		this.ultima_atualizacao_token = ultima_atualizacao_token;
	}


	public int getcodbase() {
		return codbase;
	}

	public void setcodbase(int codbase) {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getUltima_atualizacao_token() {
		return ultima_atualizacao_token;
	}

	public void setUltima_atualizacao_token(Date ultima_atualizacao_token) {
		this.ultima_atualizacao_token = ultima_atualizacao_token;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getLogin();
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

}
