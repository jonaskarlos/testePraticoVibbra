package br.com.vibbra.avalieweb.business;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.exception.BusinessException;
import br.com.vibbra.avalieweb.exception.DAOException;

public interface UsuarioBusiness extends GenericBusiness<Usuario> {
	
	public void salvar(Usuario usuario) throws Exception;
	
	public void excluir(Usuario usuario) throws Exception;
	
	public List<Usuario> pesquisarPorParametros(String email) throws DAOException;
	
	public Usuario pesquisarPorEmailPassword(String email, String password) throws Exception;
	
	public void alterarSenha(Usuario usuario) throws BusinessException;

}
