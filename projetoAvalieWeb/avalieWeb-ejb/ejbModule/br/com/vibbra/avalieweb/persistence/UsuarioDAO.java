package br.com.vibbra.avalieweb.persistence;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.exception.DAOException;

public interface UsuarioDAO extends GenericDAO<Usuario> {
	
	public List<Usuario> pesquisarPorParametros(String email) throws DAOException;
	
	public Usuario pesquisarPorEmailPassword(String email, String hashPassword) throws DAOException;
	
	public Usuario verificarLoginExistente(Usuario usuarioAtual) throws DAOException;

}
