package br.com.vibbra.avalieweb.enumeration;

public enum TipoEstabelecimentoEum {
	
	C ("Café"),
	R ("Restaurante"),
	W ("Coworking"),
	L ("Livraria"),
	O ("Outro");

	private String descricao;

	private TipoEstabelecimentoEum(String descricao) {
		this.descricao = descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
