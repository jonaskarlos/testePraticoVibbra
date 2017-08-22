package br.com.vibbra.avalieweb.enumeration;

public enum TipoEstabelecimentoEnum {
	
	C ("Café"),
	R ("Restaurante"),
	W ("Coworking"),
	L ("Livraria"),
	O ("Outro");

	private String descricao;

	private TipoEstabelecimentoEnum(String descricao) {
		this.descricao = descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
