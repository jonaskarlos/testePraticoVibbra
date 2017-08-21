package br.com.vibbra.avalieweb.persistence.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.exception.DAOException;
import br.com.vibbra.avalieweb.persistence.UsuarioDAO;
import br.com.vibbra.avalieweb.util.StringUtil;

@Name("usuarioDAO")
@AutoCreate
public class UsuarioDAOImp extends GenericDAOImp<Usuario> implements UsuarioDAO {

	private static final long serialVersionUID = 1L;
	
	@In
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> pesquisarPorParametros(String email) throws DAOException {
		if (StringUtil.isEmpty(email)){
			email = null;
		}
		Query query = getEntityManager().createNamedQuery(Usuario.NAMED_QUERY_BUSCAR_POR_PARAMETROS);
		query.setParameter("emailAux", email);
		query.setParameter("email", email);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario pesquisarPorEmailPassword(String email, String hashPassword) throws DAOException {
		Usuario usuario = null;
		Query query = getEntityManager().createNamedQuery(Usuario.NAMED_QUERY_BUSCAR_POR_EMAIL_SENHA);
		query.setParameter("email", email);
		query.setParameter("senha", hashPassword);
		List<Usuario> usuarioList = query.getResultList();
		if (usuarioList != null && usuarioList.size() > 0){
			usuario = usuarioList.get(0);
		}
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Usuario verificarLoginExistente(Usuario usuarioAtual) throws DAOException {
		Usuario usuario = null;
		Query query = getEntityManager().createNamedQuery(Usuario.NAMED_QUERY_BUSCAR_EMAIL_EXISTENTE);
		query.setParameter("email", usuarioAtual.getEmail());
		List<Usuario> usuarioList = query.getResultList();
		if (usuarioList != null && usuarioList.size() > 0){
			usuario = usuarioList.get(0);
		}
		return usuario;
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
