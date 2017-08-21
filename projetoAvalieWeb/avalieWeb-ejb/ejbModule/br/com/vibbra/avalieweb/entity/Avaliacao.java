package br.com.vibbra.avalieweb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tb_avaliacao")
public class Avaliacao extends GenericEntity {

	private static final long serialVersionUID = 5110541363624012198L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_avaliacao")
	@SequenceGenerator(name="sq_avaliacao", sequenceName="sq_avaliacao", allocationSize=1)
	@Column(name="id_avaliacao")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario ususario;
	
	@Column(name="txt_velocidade_internet")
	private String velocidadeInternet;
	
	@Column(name="flg_internet_aberta")
	private Boolean internetAberta;
	
	@Column(name="txt_senha_internet")
	private String senhaInternet;
	
	@Column(name="txt_atendimento")
	private Integer atendimento;
	
	@Column(name="txt_preco")
	private Integer preco;
	
	@Column(name="txt_conforto")
	private Integer conforto;
	
	@Column(name="txt_ruido")
	private Integer ruido;
	
	@Column(name="txt_geral")
	private Integer geral;
	
	@ManyToOne
	@JoinColumn(name="id_estabelecimento")
	private Estabelecimento estabelecimento;
	
	@OneToMany(mappedBy="avaliacao", fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AvaliacaoComida> avaliacaoComidaLista = new ArrayList<AvaliacaoComida>();
	
	@OneToMany(mappedBy="avaliacao", fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AvaliacaoBebida> avaliacaoBebidaLista = new ArrayList<AvaliacaoBebida>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsusario() {
		return ususario;
	}

	public void setUsusario(Usuario ususario) {
		this.ususario = ususario;
	}

	public String getVelocidadeInternet() {
		return velocidadeInternet;
	}

	public void setVelocidadeInternet(String velocidadeInternet) {
		this.velocidadeInternet = velocidadeInternet;
	}

	public Boolean getInternetAberta() {
		return internetAberta;
	}

	public void setInternetAberta(Boolean internetAberta) {
		this.internetAberta = internetAberta;
	}

	public String getSenhaInternet() {
		return senhaInternet;
	}

	public void setSenhaInternet(String senhaInternet) {
		this.senhaInternet = senhaInternet;
	}

	public Integer getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Integer atendimento) {
		this.atendimento = atendimento;
	}

	public Integer getPreco() {
		return preco;
	}

	public void setPreco(Integer preco) {
		this.preco = preco;
	}

	public Integer getConforto() {
		return conforto;
	}

	public void setConforto(Integer conforto) {
		this.conforto = conforto;
	}

	public Integer getRuido() {
		return ruido;
	}

	public void setRuido(Integer ruido) {
		this.ruido = ruido;
	}

	public Integer getGeral() {
		return geral;
	}

	public void setGeral(Integer geral) {
		this.geral = geral;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public List<AvaliacaoComida> getAvaliacaoComidaLista() {
		return avaliacaoComidaLista;
	}

	public void setAvaliacaoComidaLista(List<AvaliacaoComida> avaliacaoComidaLista) {
		this.avaliacaoComidaLista = avaliacaoComidaLista;
	}

	public List<AvaliacaoBebida> getAvaliacaoBebidaLista() {
		return avaliacaoBebidaLista;
	}

	public void setAvaliacaoBebidaLista(List<AvaliacaoBebida> avaliacaoBebidaLista) {
		this.avaliacaoBebidaLista = avaliacaoBebidaLista;
	}
	
}
