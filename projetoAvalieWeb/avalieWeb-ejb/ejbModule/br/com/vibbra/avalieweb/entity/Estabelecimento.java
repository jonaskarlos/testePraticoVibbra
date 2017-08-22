package br.com.vibbra.avalieweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;

@Entity
@Table(name="tb_estabelecimento")
public class Estabelecimento extends GenericEntity {

	private static final long serialVersionUID = 7172951289839585506L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_estabelecimento")
	@SequenceGenerator(name="sq_estabelecimento", sequenceName="sq_estabelecimento", allocationSize=1)
	@Column(name="id_estabelecimento")
	private Long id; 
	
	@Column(name="txt_nome")
	private String nome;
	
	@Column(name = "txt_endereco")
	private String endereco;
	
	@Column(name = "txt_cidade")
	private String cidade;
	
	@Column(name = "txt_estado")
	private String estado;
	
	@Column(name = "txt_pais")
	private String pais;
	
	@Enumerated(EnumType.STRING)
	@Column(name="txt_tipo")
	private TipoEstabelecimentoEnum tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public TipoEstabelecimentoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEstabelecimentoEnum tipo) {
		this.tipo = tipo;
	}
	
}
