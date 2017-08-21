package br.com.vibbra.avalieweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@NamedQueries({
	@NamedQuery(name = Usuario.NAMED_QUERY_BUSCAR_POR_PARAMETROS, 
			query = "select u from Usuario u " + 
					"where (:emailAux is null or u.email = :email) "),
	
	@NamedQuery (name = Usuario.NAMED_QUERY_BUSCAR_POR_EMAIL_SENHA, 
			query = "select u from Usuario u " + 
					"where u.email = :email " + 
					"  and u.senha = :senha "),
			
	@NamedQuery(name = Usuario.NAMED_QUERY_BUSCAR_EMAIL_EXISTENTE, 
			query = "select u from Usuario u " + 
					"where u.email = :email ")
})
@Entity
@Table(name="tb_usuario")
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
public class Usuario extends GenericEntity {
	
	public static final String NAMED_QUERY_BUSCAR_POR_PARAMETROS="buscarPorParametros";
	public static final String NAMED_QUERY_BUSCAR_POR_EMAIL_SENHA="buscarPorLoginSenha";
	public static final String NAMED_QUERY_BUSCAR_EMAIL_EXISTENTE="buscarLoginExistente";

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_usuario")
	@SequenceGenerator(name="sq_usuario", sequenceName="sq_usuario", allocationSize=1)
	@Column(name="id_usuario")
	private Long id;
	
	@Column(name = "txt_email")
	private String email;
	
	@Column(name = "txt_senha")
	private String senha;

	@Column(name = "txt_endereco")
	private String endereco;
	
	@Column(name = "txt_cidade")
	private String cidade;
	
	@Column(name = "txt_estado")
	private String estado;
	
	@Column(name = "txt_pais")
	private String pais;
	
	
	@Transient
	private String senhaConfirmacao;
	
	@Transient
	private String novaSenha;
	
	@Transient
	private String senhaAtual;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	
}
