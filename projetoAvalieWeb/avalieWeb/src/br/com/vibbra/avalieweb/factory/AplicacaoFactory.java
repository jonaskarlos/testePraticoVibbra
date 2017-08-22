package br.com.vibbra.avalieweb.factory;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.business.EstabelecimentoBusiness;
import br.com.vibbra.avalieweb.business.UsuarioBusiness;
import br.com.vibbra.avalieweb.entity.Estabelecimento;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;

@Name("aplicacaoFactory")
public class AplicacaoFactory {
	
	@In
	private EstabelecimentoBusiness estabelecimentoBusiness;
	
	@In
	private UsuarioBusiness usuarioBusiness;
	
	@Factory(value="tipoEstabelecimentoListaFactory", scope=ScopeType.SESSION)
	public TipoEstabelecimentoEnum[] getTipoProcessamentoArray() {
		return TipoEstabelecimentoEnum.values();
	}
	
	@Factory(value="estabelecimentoListaFactory", scope=ScopeType.CONVERSATION)
	public List<Estabelecimento> pesquisarEstabelecimentos(){
		return estabelecimentoBusiness.findAll("nome");		
	}
	
	@Factory(value="usuarioListaFactory", scope=ScopeType.CONVERSATION)
	public List<Usuario> pesquisarUsuarios(){
		return usuarioBusiness.findAll("email");		
	}
	
	
	/*@In
	private EstadoBusiness estadoBusiness;
	
	@In
	private CidadeBusiness cidadeBusiness;
	
	@In
	private ProdutoBusiness produtoBusiness;
	
	private List<Cidade> listaCidades = new ArrayList<Cidade>();
	
	@Factory(value="estadosFactory", scope=ScopeType.SESSION)
	public List<Estado> pesquisarEstados(){
		return estadoBusiness.listarOrdenadoPorSigla();		
	}
	
	@Factory(value="cidadesFactory", scope=ScopeType.SESSION)
	public List<Cidade> pesquisarCidades(){
		listaCidades = cidadeBusiness.findAll("nome");
		return listaCidades;
	}
	
	@Factory(value="produtosFactory", scope=ScopeType.SESSION)
	public List<Produto> pesquisarProdutos(){
		return produtoBusiness.findAll("nome"); 
	}
	
	@Factory(value="situacaoProdutoSerialListaFactory", scope=ScopeType.CONVERSATION)
	public SituacaoProdutoSerialEnum[] getSituacaoProdutoSerialArray() {
		return SituacaoProdutoSerialEnum.values();
	} 

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}*/
	
	

}
