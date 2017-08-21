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
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import br.com.vibbra.avalieweb.business.UsuarioBusiness;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.exception.BusinessException;

@Name("usuarioAction")
@Scope(ScopeType.CONVERSATION)
public class UsuarioAction extends GenericAction {

	private static final long serialVersionUID = 1L;
	
	private static final String LISTAR_USUARIO = "listarUsuario";
	private static final String ADICIONAR_USUARIO = "adicionarUsuario";
	private static final String EDITAR_USUARIO = "editarUsuario";
	private static final String ALTERAR_SENHA_USUARIO = "alterarSenhaUsuario";
	private static final String USUARIO = "Usuario ";
	
	@In(create=true)
	private UsuarioBusiness usuarioBusiness;
	
	@In 
	Identity identity;
	@In 
	Credentials credentials;
	
	private Usuario usuario;
	
	private Usuario usuarioPesquisa;
	
	private Usuario usuarioEdicao;
	
	private List<Usuario> usuarioLista;
	
	private Usuario usuarioExclusao;
	
	private boolean modoEdicao;
	private boolean exibirModalExclusao;
	
	@Create
	@Begin(join = true)
	public void create(){
		usuarioEdicao = new Usuario();
		this.limpar();
		pesquisar();
	}
	
	public String limpar(){
		exibirModalExclusao = false;
		modoEdicao = false;
		usuarioPesquisa = new Usuario();
		usuarioLista = new ArrayList<Usuario>();
		return LISTAR_USUARIO;
	}
	
	public String pesquisar(){
		try{
			usuarioLista = usuarioBusiness.pesquisarPorParametros(usuarioPesquisa.getEmail());
			
			if (usuarioLista == null || usuarioLista.size() == 0) {
				jsfUtil.showWarnMessage("pesquisa.sem.resultados");
			}
			
		}catch(BusinessException e){
			jsfUtil.showErrorMessage(e.getMessage());
		}
		return LISTAR_USUARIO;
	}
	
	
	@Begin(nested = true)
	public String adicionar() {
		this.prepararAdicao();
		return ADICIONAR_USUARIO;
	}
	
	private void prepararAdicao() {
		usuarioEdicao = new Usuario();
	}

	public String limparCamposEdicao() {
		this.prepararAdicao();
		return EDITAR_USUARIO;
	}
	
	@Begin(nested = true)
	public String editar(Usuario usuarioBean) {
		usuarioEdicao = usuarioBean;
		modoEdicao = true;
		return EDITAR_USUARIO;
	}
	
	@Begin(nested = true)
	public String prepararAlterarSenha(Usuario usuarioBean){
		usuarioEdicao = usuarioBean;
		usuarioEdicao.setSenhaAtual(usuarioBean.getSenha());
		return ALTERAR_SENHA_USUARIO;
	}
	
	public void prepararExclusao(Usuario usuarioBean) {
		this.usuarioExclusao = usuarioBean;
		exibirModalExclusao = true;
	}

	public void cancelarExclusao() {
		exibirModalExclusao = false;
		usuarioExclusao = null;
	}

	public void excluir() {
		try {
			exibirModalExclusao = false;
			usuarioBusiness.remove(usuarioExclusao);
			this.pesquisar();
			jsfUtil.showInfoMessage("excluido.sucesso", USUARIO + " - "+ usuarioExclusao.getEmail());
		} catch (BusinessException e) {
			jsfUtil.showWarnMessage(e.getMessage(), USUARIO);
		}
	}

	@End(beforeRedirect = true)
	public String salvar() {
		String retorno = LISTAR_USUARIO;
		try {
			
			usuarioBusiness.salvar(usuarioEdicao);
			
			jsfUtil.setSessionAttribute("usuarioLogado", usuarioEdicao);

			if (modoEdicao)
				jsfUtil.showInfoMessage("atualizado.sucesso", USUARIO + " - " + usuarioEdicao.getEmail());
			else {
				jsfUtil.showInfoMessage("salvo.sucesso", USUARIO + " - " + usuarioEdicao.getEmail());
			}
			
			if (usuarioLista != null && usuarioLista.size() > 0){
				this.pesquisar();
			}
			modoEdicao = false;
			
		} catch (Exception e) {
			jsfUtil.showWarnMessage(e.getMessage(), USUARIO);
			retorno = EDITAR_USUARIO;
		}
		return retorno;
	}
	
	@End(beforeRedirect=true)
	private String finalizarAlteracaoSenha(){
		jsfUtil.showInfoMessage("usuario.senha.alterada.com.sucesso");
		if (usuarioLista != null && usuarioLista.size() > 0){
			this.pesquisar();
		}
		return LISTAR_USUARIO;
	}
	
	@End(beforeRedirect=true)
	public String alterarSenha(){
		String retorno = ALTERAR_SENHA_USUARIO;
		try{
			usuarioBusiness.alterarSenha(usuarioEdicao);
			return finalizarAlteracaoSenha();
			
		}catch(BusinessException e){
			jsfUtil.showWarnMessage(e.getMessage(), USUARIO);
		}
		
		return retorno;
		
	}
	
	
	@End(beforeRedirect = true)
	public String voltar() {
		if (usuarioLista != null && usuarioLista.size() > 0)
			this.pesquisar();

		modoEdicao = false;
		return LISTAR_USUARIO;
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioPesquisa() {
		return usuarioPesquisa;
	}

	public void setUsuarioPesquisa(Usuario usuarioPesquisa) {
		this.usuarioPesquisa = usuarioPesquisa;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

	public List<Usuario> getUsuarioLista() {
		return usuarioLista;
	}

	public void setUsuarioLista(List<Usuario> usuarioLista) {
		this.usuarioLista = usuarioLista;
	}

	public Usuario getUsuarioExclusao() {
		return usuarioExclusao;
	}

	public void setUsuarioExclusao(Usuario usuarioExclusao) {
		this.usuarioExclusao = usuarioExclusao;
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
