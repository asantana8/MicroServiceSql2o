package br.jus.trf5.dominio.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"), GESTOR(2, "ROLE_GESTOR"), SERVIDOR(3, "ROLE_SERVIDOR"), SISTEMA(4, "ROLE_SISTEMA");

	private int id;
	private String descricao;

	private Perfil(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
