package br.com.vibbra.avalieweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tb_avaliacao_comida")

@NamedQueries({
	@NamedQuery(name = AvaliacaoComida.NAMED_QUERY_PESQUISAR_COMIDA_POR_AVALIACAO,
			query = "select c from AvaliacaoComida c " +
					"inner join c.avaliacao a " +
					"where a.id = :avaliacaoId " +
					"order by c.tipoComida ) ")	
})

public class AvaliacaoComida extends GenericEntity {

	private static final long serialVersionUID = 4373525388442317832L;
	
	public static final String NAMED_QUERY_PESQUISAR_COMIDA_POR_AVALIACAO="pesquisarComidaPorAvaliacao";

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_avaliacao_comida")
	@SequenceGenerator(name="sq_avaliacao_comida", sequenceName="sq_avaliacao_comida", allocationSize=1)
	@Column(name="id_avaliacao_comida")
	private Long id; 
	
	@ManyToOne
	@JoinColumn(name="id_avaliacao")
	private Avaliacao avaliacao;
	
	@Column(name="txt_tipo_comida")
	private String tipoComida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

}
