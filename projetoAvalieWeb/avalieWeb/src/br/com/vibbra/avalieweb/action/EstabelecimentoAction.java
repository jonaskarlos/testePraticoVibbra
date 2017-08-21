package br.com.vibbra.avalieweb.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.vibbra.avalieweb.business.EstabelecimentoBusiness;
import br.com.vibbra.avalieweb.entity.Estabelecimento;
import br.com.vibbra.avalieweb.exception.BusinessException;

@Name("estabelecimentoAction")
@Scope(ScopeType.CONVERSATION)
public class EstabelecimentoAction extends GenericAction {

	private static final long serialVersionUID = 1L;
	
	private static final String LISTAR_ESTABELECIMENTO = "listarEstabelecimento";
	private static final String ADICIONAR_ESTABELECIMENTO = "adicionarEstabelecimento";
	private static final String EDITAR_ESTABELECIMENTO = "editarEstabelecimento";
	private static final String ESTABELECIMENTO = "Estabelecimento ";
	
	@In(create=true)
	private EstabelecimentoBusiness estabelecimentoBusiness;
	
	private Estabelecimento estabelecimento;
	
	private Estabelecimento estabelecimentoPesquisa;
	
	private Estabelecimento estabelecimentoEdicao;
	
	private List<Estabelecimento> estabelecimentoLista;
	
	private Estabelecimento estabelecimentoExclusao;
	
	private boolean modoEdicao;
	private boolean exibirModalExclusao;
	
	@Create
	@Begin(join = true)
	public void create(){
		this.limpar();
		pesquisar();
	}
	
	public String limpar(){
		exibirModalExclusao = false;
		modoEdicao = false;
		estabelecimentoPesquisa = new Estabelecimento();
		estabelecimentoLista = new ArrayList<Estabelecimento>();
		return LISTAR_ESTABELECIMENTO;
	}
	
	public String pesquisar(){
		try{
			estabelecimentoLista = estabelecimentoBusiness.findAll();
			
			if (estabelecimentoLista == null || estabelecimentoLista.size() == 0) {
				jsfUtil.showWarnMessage("pesquisa.sem.resultados");
			}
			
		}catch(BusinessException e){
			jsfUtil.showErrorMessage(e.getMessage());
		}
		return LISTAR_ESTABELECIMENTO;
	}
	
	@Begin(nested = true)
	public String adicionar() {
		this.prepararAdicao();
		return ADICIONAR_ESTABELECIMENTO;
	}
	
	private void prepararAdicao() {
		estabelecimentoEdicao = new Estabelecimento();
	}

	public String limparCamposEdicao() {
		this.prepararAdicao();
		return EDITAR_ESTABELECIMENTO;
	}
	
	@Begin(nested = true)
	public String editar(Estabelecimento estabelecimentoBean) {
		estabelecimentoEdicao = estabelecimentoBean;
		modoEdicao = true;
		return EDITAR_ESTABELECIMENTO;
	}
	
	
	public void prepararExclusao(Estabelecimento estabelecimentoBean) {
		this.estabelecimentoExclusao = estabelecimentoBean;
		exibirModalExclusao = true;
	}

	public void cancelarExclusao() {
		exibirModalExclusao = false;
		estabelecimentoExclusao = null;
	}

	public void excluir() {
		try {
			exibirModalExclusao = false;
			estabelecimentoBusiness.remove(estabelecimentoExclusao);
			this.pesquisar();
			jsfUtil.showInfoMessage("excluido.sucesso", ESTABELECIMENTO + " - "+ estabelecimentoExclusao.getNome());
		} catch (BusinessException e) {
			jsfUtil.showWarnMessage(e.getMessage(), ESTABELECIMENTO);
		}
	}

	@End(beforeRedirect = true)
	public String salvar() {
		String retorno = LISTAR_ESTABELECIMENTO;
		try {
			
			estabelecimentoBusiness.save(estabelecimentoEdicao);

			if (modoEdicao)
				jsfUtil.showInfoMessage("atualizado.sucesso", ESTABELECIMENTO + " - " + estabelecimentoEdicao.getNome());
			else {
				jsfUtil.showInfoMessage("salvo.sucesso", ESTABELECIMENTO + " - " + estabelecimentoEdicao.getNome());
			}
			
			if (estabelecimentoLista != null && estabelecimentoLista.size() > 0){
				this.pesquisar();
			}
			modoEdicao = false;
			
		} catch (Exception e) {
			jsfUtil.showWarnMessage(e.getMessage(), ESTABELECIMENTO);
			retorno = EDITAR_ESTABELECIMENTO;
		}
		return retorno;
	}
	
	@End(beforeRedirect=true)
	private String finalizarAlteracaoSenha(){
		jsfUtil.showInfoMessage("estabelecimento.senha.alterada.com.sucesso");
		if (estabelecimentoLista != null && estabelecimentoLista.size() > 0){
			this.pesquisar();
		}
		return LISTAR_ESTABELECIMENTO;
	}
	
	
	@End(beforeRedirect = true)
	public String voltar() {
		if (estabelecimentoLista != null && estabelecimentoLista.size() > 0)
			this.pesquisar();

		modoEdicao = false;
		return LISTAR_ESTABELECIMENTO;
	}
	

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimentoPesquisa() {
		return estabelecimentoPesquisa;
	}

	public void setEstabelecimentoPesquisa(Estabelecimento estabelecimentoPesquisa) {
		this.estabelecimentoPesquisa = estabelecimentoPesquisa;
	}

	public Estabelecimento getEstabelecimentoEdicao() {
		return estabelecimentoEdicao;
	}

	public void setEstabelecimentoEdicao(Estabelecimento estabelecimentoEdicao) {
		this.estabelecimentoEdicao = estabelecimentoEdicao;
	}

	public List<Estabelecimento> getEstabelecimentoLista() {
		return estabelecimentoLista;
	}

	public void setEstabelecimentoLista(List<Estabelecimento> estabelecimentoLista) {
		this.estabelecimentoLista = estabelecimentoLista;
	}

	public Estabelecimento getEstabelecimentoExclusao() {
		return estabelecimentoExclusao;
	}

	public void setEstabelecimentoExclusao(Estabelecimento estabelecimentoExclusao) {
		this.estabelecimentoExclusao = estabelecimentoExclusao;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public boolean isExibirModalExclusao() {
		return exibirModalExclusao;
	}

	public void setExibirModalExclusao(boolean exibirModalExclusao) {
		this.exibirModalExclusao = exibirModalExclusao;
	}

}
