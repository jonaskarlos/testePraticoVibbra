package br.com.vibbra.avalieweb.persistence.imp;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.entity.Estabelecimento;
import br.com.vibbra.avalieweb.persistence.EstabelecimentoDAO;

@Name("estabelecimentoDAO")
@AutoCreate
public class EstabelecimentoDAOImp extends GenericDAOImp<Estabelecimento> implements EstabelecimentoDAO {

	private static final long serialVersionUID = -7578877670759142556L;
	
	@In
	EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
