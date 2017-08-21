package br.com.vibbra.avalieweb.business.imp;

import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.business.UsuarioBusiness;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.exception.BusinessException;
import br.com.vibbra.avalieweb.exception.DAOException;
import br.com.vibbra.avalieweb.persistence.UsuarioDAO;

@Name("usuarioBusiness")
@AutoCreate
public class UsuarioBusinessImp extends GenericBusinessImp<Usuario, UsuarioDAO> implements UsuarioBusiness {

	private static final long serialVersionUID = 1L;
	
	@In
	UsuarioDAO usuarioDAO;
	
	@Override
	public void salvar(Usuario usuario) throws Exception {
		save(usuario);
	}

	@Override
	public void excluir(Usuario usuario) throws Exception {

	}

	@Override
	public List<Usuario> pesquisarPorParametros(String email) throws DAOException {
		return getDao().pesquisarPorParametros(email);
	}

	@Override
	public Usuario pesquisarPorEmailPassword(String email, String password) throws Exception {
		return getDao().pesquisarPorEmailPassword(email, password);
	}
	
	@Override
	public UsuarioDAO getDao() {
		return usuarioDAO;
	}
	
	@Override
	protected void validateBusinessRules(Usuario usuario, boolean isUpdating) throws BusinessException {
		if (!isUpdating){
			//Verificando login existente
			verificarLoginExistente(usuario);
			
			//Verificando confirmação de Senha
			//verificarConfirmacaoSenha(usuario);
		}
	}
	
	private void verificarLoginExistente(Usuario usuario) throws BusinessException{
		Usuario usuarioExistente = getDao().verificarLoginExistente(usuario);
		if (usuarioExistente != null){
			throw new BusinessException("usuario.login.existente");
		}
	}
	
	private void verificarConfirmacaoSenha(Usuario usuario) throws BusinessException{
		if (!usuario.getSenha().equals(usuario.getSenhaConfirmacao())){
			throw new BusinessException("usuario.confirmacao.senha.nao.confere");
		}
	}
	
	private void verificarConfirmacaoNovaSenha(Usuario usuario) throws BusinessException{
		if (!usuario.getNovaSenha().equals(usuario.getSenhaConfirmacao())){
			throw new BusinessException("usuario.confiramcao.nova.senha.nao.confere");
		}
	}
	
	@Override
	public void alterarSenha(Usuario usuario) throws BusinessException {
		try{
			this.verificarSenhaAtual(usuario);
			this.verificarConfirmacaoNovaSenha(usuario);
			usuario.setSenha(usuario.getNovaSenha());
			getDao().merge(usuario);
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	private void verificarSenhaAtual(Usuario usuario) throws Exception{
		//String hashPassword = getHash(usuario.getSenha(), usuario.getLogin());
		if (!usuario.getSenha().equals(usuario.getSenhaAtual())){
			throw new BusinessException("usuario.senha.atual.nao.confere");
		}
	}

}
