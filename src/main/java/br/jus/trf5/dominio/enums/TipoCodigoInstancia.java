package br.jus.trf5.dominio.enums;

public enum TipoCodigoInstancia {

	H2(0, "H2DataBase"), OR(1, "Oracle"), PG(2, "PostGreSql");

	private int id;
	private String descricao;

	private TipoCodigoInstancia(int id, String descricao) {
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

	public static TipoCodigoInstancia valueOf(int value) {
		switch (value) {
		case 0:
			return TipoCodigoInstancia.H2;
		case 1:
			return TipoCodigoInstancia.OR;
		case 2:
			return TipoCodigoInstancia.PG;
		default:
			return null;
		}
	}
}
