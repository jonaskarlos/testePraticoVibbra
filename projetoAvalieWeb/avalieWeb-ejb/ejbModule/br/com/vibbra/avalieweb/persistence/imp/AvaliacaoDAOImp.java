package br.com.vibbra.avalieweb.persistence.imp;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.persistence.AvaliacaoDAO;

@Name("avaliacaoDAO")
@AutoCreate
public class AvaliacaoDAOImp extends GenericDAOImp<Avaliacao> implements AvaliacaoDAO {

	private static final long serialVersionUID = -3333551988675428044L;
	
	@In
	EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
