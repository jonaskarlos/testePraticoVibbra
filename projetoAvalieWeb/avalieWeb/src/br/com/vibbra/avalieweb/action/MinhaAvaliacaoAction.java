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

import br.com.vibbra.avalieweb.business.AvaliacaoBebidaBusiness;
import br.com.vibbra.avalieweb.business.AvaliacaoBusiness;
import br.com.vibbra.avalieweb.business.AvaliacaoComidaBusiness;
import br.com.vibbra.avalieweb.business.UsuarioBusiness;
import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoBebida;
import br.com.vibbra.avalieweb.entity.AvaliacaoComida;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;
import br.com.vibbra.avalieweb.exception.BusinessException;

@Name("minhaAvaliacaoAction")
@Scope(ScopeType.CONVERSATION)
public class MinhaAvaliacaoAction extends GenericAction {

	private static final long serialVersionUID = 1L;
	
	private static final String LISTAR_MINHA_AVALIACAO = "listarMinhaAvaliacao";
	private static final String ADICIONAR_MINHA_AVALIACAO = "adicionarMinhaAvaliacao";
	private static final String EDITAR_MINHA_AVALIACAO = "editarMinhaAvaliacao";
	private static final String AVALIACAO = "Avaliacao ";
	private static final String TIPO_COMIDA = "Tipo de Comida";
	private static final String TIPO_BEBIDA = "Tipo de Bebida";
	
	
	@In(create=true)
	private AvaliacaoBusiness avaliacaoBusiness;
	
	@In(create=true)
	private UsuarioBusiness usuarioBusiness;
	
	@In(create=true)
	private AvaliacaoComidaBusiness avaliacaoComidaBusiness;
	
	@In(create=true)
	private AvaliacaoBebidaBusiness avaliacaoBebidaBusiness;
	
	private Avaliacao avaliacao;
	
	private Usuario usuario;
	
	private Usuario usuarioLogado;
	
	private Avaliacao avaliacaoPesquisa;
	
	private TipoEstabelecimentoEnum tipoEstabelecimentoPesquisa;
	
	private String enderecoPesquisa;
	
	private Avaliacao avaliacaoEdicao;
	
	private List<Avaliacao> avaliacaoLista;
	private List<AvaliacaoComida> avaliacaoComidaLista;
	private List<AvaliacaoBebida> avaliacaoBebidaLista;
	
	private Avaliacao avaliacaoExclusao;
	
	private AvaliacaoComida avaliacaoComida;
	private AvaliacaoBebida avaliacaoBebida;
	
	private boolean modoEdicao;
	private boolean modoEdicaoComida;
	private boolean modoEdicaoBebida;
	
	private boolean exibirModalExclusao;
	private boolean exibirModalExclusaoTipoComida;
	private boolean exibirModalExclusaoTipoBebida;
	
	private boolean exibirModalAdicaoComida;
	private boolean exibirModalAdicaoBebida;
	
	@Create
	@Begin(join = true)
	public void create(){
		this.limpar();
	}
	
	public String limpar(){
		exibirModalExclusao = false;
		exibirModalAdicaoBebida = false;
		exibirModalAdicaoComida = false;
		modoEdicao = false;
		modoEdicaoBebida = false;
		modoEdicaoComida = false;
		avaliacaoPesquisa = new Avaliacao();
		usuario = new Usuario();
		avaliacaoLista = new ArrayList<Avaliacao>();
		return LISTAR_MINHA_AVALIACAO;
	}
	
	public String pesquisar(){
		try{
			if (usuarioLogado != null){
				//avaliacaoLista = avaliacaoBusiness.pesquisarAvalicaoPorUsuario(usuarioLogado);
				avaliacaoLista = avaliacaoBusiness.pesquisarAvaliacaoPorUsuarioTipoEndereco(usuarioLogado, 
						tipoEstabelecimentoPesquisa, enderecoPesquisa);
			}
			
			if (avaliacaoLista == null || avaliacaoLista.size() == 0) {
				jsfUtil.showWarnMessage("pesquisa.sem.resultados");
			}
			
		}catch(BusinessException e){
			jsfUtil.showErrorMessage(e.getMessage());
		}
		return LISTAR_MINHA_AVALIACAO;
	}
	
	public String realizarLogin(){
		Usuario usuarioPesquisa = null;
		String retorno = "";
		try {
			usuarioPesquisa = usuarioBusiness.pesquisarPorEmailPassword(usuario.getEmail(), usuario.getSenha());
			if (usuarioPesquisa != null){
				jsfUtil.setSessionAttribute("usuarioLogado", usuarioLogado);
				usuarioLogado = usuarioPesquisa; 
				jsfUtil.showInfoMessage("login.realizado.sucesso");
				retorno = pesquisar();
			}else{
				jsfUtil.showWarnMessage("login.inexistente");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	@Begin(nested = true)
	public String adicionar() {
		this.prepararAdicao();
		return ADICIONAR_MINHA_AVALIACAO;
	}
	
	private void prepararAdicao() {
		avaliacaoEdicao = new Avaliacao();
		if (usuarioLogado != null){
			avaliacaoEdicao.setUsuario(usuarioLogado);
		}
		
	}
	
	public void prepararAdicionarComida(Avaliacao avaliacaoBean){
		avaliacaoComida = new AvaliacaoComida();
		avaliacaoComida.setAvaliacao(avaliacaoBean);
		exibirModalAdicaoComida = true;
		modoEdicaoComida = true;
		modoEdicaoBebida = false;
	}
	
	public void prepararAdicionarBebida(Avaliacao avaliacaoBean){
		avaliacaoBebida = new AvaliacaoBebida();
		avaliacaoBebida.setAvaliacao(avaliacaoBean);
		exibirModalAdicaoBebida = true;
		modoEdicaoBebida = true;
		modoEdicaoComida = false;
	}

	public String limparCamposEdicao() {
		this.prepararAdicao();
		return EDITAR_MINHA_AVALIACAO;
	}
	
	@Begin(nested = true)
	public String editar(Avaliacao avaliacaoBean) {
		avaliacaoEdicao = avaliacaoBean;
		avaliacaoComidaLista = avaliacaoComidaBusiness.pesquisarPorAvaliacao(avaliacaoEdicao);
		avaliacaoBebidaLista = avaliacaoBebidaBusiness.pesquisarPorAvaliacao(avaliacaoEdicao);
		modoEdicao = true;
		return EDITAR_MINHA_AVALIACAO;
	}
	
	
	public void prepararExclusao(Avaliacao avaliacaoBean) {
		this.avaliacaoExclusao = avaliacaoBean;
		exibirModalExclusao = true;
	}
	
	public void prepararExclusaoComida(AvaliacaoComida avaliacaoComidaBean){
		this.avaliacaoComida = avaliacaoComidaBean;
		exibirModalExclusaoTipoComida = true;
	}
	
	public void prepararExclusaoBebida(AvaliacaoBebida avaliacaoBebidaBean){
		this.avaliacaoBebida = avaliacaoBebidaBean;
		exibirModalExclusaoTipoBebida = true;
	}

	public void cancelarExclusao() {
		exibirModalExclusao = false;
		avaliacaoExclusao = null;
	}
	
	public void cancelarExclusaoComida(){
		exibirModalExclusaoTipoComida = false;
		avaliacaoComida = null;
	}
	
	public void cancelarExclusaoBebida(){
		exibirModalExclusaoTipoBebida = false;
		avaliacaoBebida = null;
	}
	
	public void cancelarAddComida(){
		exibirModalAdicaoComida = false;
	}
	
	public void cancelarAddBebida(){
		exibirModalAdicaoBebida = false;
	}
	

	public void excluir() {
		try {
			exibirModalExclusao = false;
			avaliacaoBusiness.remove(avaliacaoExclusao);
			this.pesquisar();
			jsfUtil.showInfoMessage("excluido.sucesso", AVALIACAO + " - "+ avaliacaoExclusao.getEstabelecimento().getNome());
		} catch (BusinessException e) {
			jsfUtil.showWarnMessage(e.getMessage(), AVALIACAO);
		}
	}
	
	public void excluirTipoComida(){
		try{
			exibirModalAdicaoComida = false;
			exibirModalExclusaoTipoComida = false;
			avaliacaoComidaBusiness.remove(avaliacaoComida);
			avaliacaoComidaLista = avaliacaoComidaBusiness.pesquisarPorAvaliacao(avaliacaoEdicao);
			jsfUtil.showInfoMessage("excluido.sucesso", TIPO_COMIDA + " - "+ avaliacaoComida.getTipoComida());
		}catch(BusinessException e){
			jsfUtil.showWarnMessage(e.getMessage(), TIPO_COMIDA);
		}
	}
	
	public void excluirTipoBebida(){
		try{
			exibirModalAdicaoBebida = false;
			exibirModalExclusaoTipoBebida = false;
			avaliacaoBebidaBusiness.remove(avaliacaoBebida);
			avaliacaoBebidaLista = avaliacaoBebidaBusiness.pesquisarPorAvaliacao(avaliacaoEdicao);
			jsfUtil.showInfoMessage("excluido.sucesso", TIPO_BEBIDA + " - "+ avaliacaoBebida.getTipoBebida());
		}catch(BusinessException e){
			jsfUtil.showWarnMessage(e.getMessage(), TIPO_BEBIDA);
		}
	}

	@End(beforeRedirect = true)
	public String salvar() {
		String retorno = LISTAR_MINHA_AVALIACAO;
		try {
			
			avaliacaoBusiness.save(avaliacaoEdicao);

			if (modoEdicao)
				jsfUtil.showInfoMessage("atualizado.sucesso", AVALIACAO + " - " + avaliacaoEdicao.getEstabelecimento().getNome());
			else {
				jsfUtil.showInfoMessage("salvo.sucesso", AVALIACAO + " - " + avaliacaoEdicao.getEstabelecimento().getNome());
			}
			
			if (avaliacaoLista != null && avaliacaoLista.size() > 0){
				this.pesquisar();
			}
			modoEdicao = false;
			
		} catch (Exception e) {
			jsfUtil.showWarnMessage(e.getMessage(), AVALIACAO);
			retorno = EDITAR_MINHA_AVALIACAO;
		}
		return retorno;
	}
	
	public String salvarTipoComida(){
		String retorno = "";
		try{
			avaliacaoComidaBusiness.save(avaliacaoComida);
			jsfUtil.showInfoMessage("salva.sucesso", TIPO_COMIDA, avaliacaoComida.getTipoComida());
			retorno = EDITAR_MINHA_AVALIACAO;
			avaliacaoComidaLista = avaliacaoComidaBusiness.pesquisarPorAvaliacao(avaliacaoEdicao);
		}catch(BusinessException e){
			jsfUtil.showWarnMessage(e.getMessage(), TIPO_COMIDA);
		}finally{
			exibirModalAdicaoComida = false;
		}
		return retorno;
	}
	
	public String salvarTipoBebida(){
		String retorno = "";
		try{
			avaliacaoBebidaBusiness.save(avaliacaoBebida);
			jsfUtil.showInfoMessage("salva.sucesso", TIPO_BEBIDA, avaliacaoBebida.getTipoBebida());
			avaliacaoBebidaLista = avaliacaoBebidaBusiness.pesquisarPorAvaliacao(avaliacaoEdicao);
			retorno = EDITAR_MINHA_AVALIACAO;
		}catch(BusinessException e){
			jsfUtil.showWarnMessage(e.getMessage(), TIPO_BEBIDA);
		}finally{
			exibirModalAdicaoBebida = false;
		}
		return retorno;
	}
	
	@End(beforeRedirect = true)
	public String voltar() {
		if (avaliacaoLista != null && avaliacaoLista.size() > 0)
			this.pesquisar();

		modoEdicao = false;
		return LISTAR_MINHA_AVALIACAO;
	}
	

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Avaliacao getAvaliacaoPesquisa() {
		return avaliacaoPesquisa;
	}

	public void setAvaliacaoPesquisa(Avaliacao avaliacaoPesquisa) {
		this.avaliacaoPesquisa = avaliacaoPesquisa;
	}

	public Avaliacao getAvaliacaoEdicao() {
		return avaliacaoEdicao;
	}

	public void setAvaliacaoEdicao(Avaliacao avaliacaoEdicao) {
		this.avaliacaoEdicao = avaliacaoEdicao;
	}

	public List<Avaliacao> getAvaliacaoLista() {
		return avaliacaoLista;
	}

	public void setAvaliacaoLista(List<Avaliacao> avaliacaoLista) {
		this.avaliacaoLista = avaliacaoLista;
	}

	public Avaliacao getAvaliacaoExclusao() {
		return avaliacaoExclusao;
	}

	public void setAvaliacaoExclusao(Avaliacao avaliacaoExclusao) {
		this.avaliacaoExclusao = avaliacaoExclusao;
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

	public AvaliacaoComida getAvaliacaoComida() {
		return avaliacaoComida;
	}

	public void setAvaliacaoComida(AvaliacaoComida avaliacaoComida) {
		this.avaliacaoComida = avaliacaoComida;
	}

	public AvaliacaoBebida getAvaliacaoBebida() {
		return avaliacaoBebida;
	}

	public void setAvaliacaoBebida(AvaliacaoBebida avaliacaoBebida) {
		this.avaliacaoBebida = avaliacaoBebida;
	}

	public boolean isExibirModalAdicaoComida() {
		return exibirModalAdicaoComida;
	}

	public void setExibirModalAdicaoComida(boolean exibirModalAdicaoComida) {
		this.exibirModalAdicaoComida = exibirModalAdicaoComida;
	}

	public boolean isExibirModalAdicaoBebida() {
		return exibirModalAdicaoBebida;
	}

	public void setExibirModalAdicaoBebida(boolean exibirModalAdicaoBebida) {
		this.exibirModalAdicaoBebida = exibirModalAdicaoBebida;
	}

	public boolean isModoEdicaoComida() {
		return modoEdicaoComida;
	}

	public void setModoEdicaoComida(boolean modoEdicaoComida) {
		this.modoEdicaoComida = modoEdicaoComida;
	}

	public boolean isModoEdicaoBebida() {
		return modoEdicaoBebida;
	}

	public void setModoEdicaoBebida(boolean modoEdicaoBebida) {
		this.modoEdicaoBebida = modoEdicaoBebida;
	}

	public boolean isExibirModalExclusaoTipoComida() {
		return exibirModalExclusaoTipoComida;
	}

	public void setExibirModalExclusaoTipoComida(
			boolean exibirModalExclusaoTipoComida) {
		this.exibirModalExclusaoTipoComida = exibirModalExclusaoTipoComida;
	}

	public boolean isExibirModalExclusaoTipoBebida() {
		return exibirModalExclusaoTipoBebida;
	}

	public void setExibirModalExclusaoTipoBebida(
			boolean exibirModalExclusaoTipoBebida) {
		this.exibirModalExclusaoTipoBebida = exibirModalExclusaoTipoBebida;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public TipoEstabelecimentoEnum getTipoEstabelecimentoPesquisa() {
		return tipoEstabelecimentoPesquisa;
	}

	public void setTipoEstabelecimentoPesquisa(
			TipoEstabelecimentoEnum tipoEstabelecimentoPesquisa) {
		this.tipoEstabelecimentoPesquisa = tipoEstabelecimentoPesquisa;
	}

	public String getEnderecoPesquisa() {
		return enderecoPesquisa;
	}

	public void setEnderecoPesquisa(String enderecoPesquisa) {
		this.enderecoPesquisa = enderecoPesquisa;
	}
	
	
}
