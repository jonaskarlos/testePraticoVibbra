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
@Table(name="tb_avaliacao_bebida")

@NamedQueries({
	@NamedQuery(name = AvaliacaoBebida.NAMED_QUERY_PESQUISAR_BEBIDA_POR_AVALIACAO, 
			query = "select b from AvaliacaoBebida b " +
					"inner join b.avaliacao a " +
					"where a.id = :avaliacaoId " + 
					"order by b.tipoBebida ")
	
})
public class AvaliacaoBebida extends GenericEntity {

	private static final long serialVersionUID = 6099029423091642298L;
	
	public static final String NAMED_QUERY_PESQUISAR_BEBIDA_POR_AVALIACAO="pesquisarBebidaPorAvaliacao";

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_avaliacao_bebida")
	@SequenceGenerator(name="sq_avaliacao_bebida", sequenceName="sq_avaliacao_bebida", allocationSize=1)
	@Column(name="id_avaliacao_bebida")
	private Long id; 
	
	@ManyToOne
	@JoinColumn(name="id_avaliacao")
	private Avaliacao avaliacao;
	
	@Column(name="txt_tipo_bebida")
	private String tipoBebida;

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

	public String getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(String tipoBebida) {
		this.tipoBebida = tipoBebida;
	}
	
}
