package br.com.vibbra.avalieweb.persistence.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoComida;
import br.com.vibbra.avalieweb.persistence.AvaliacaoComidaDAO;

@Name("avaliacaoComidaDAO")
@AutoCreate
public class AvaliacaoComidaDAOImp extends GenericDAOImp<AvaliacaoComida> implements AvaliacaoComidaDAO {

	private static final long serialVersionUID = -4837892864092002660L;
	
	@In
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AvaliacaoComida> pesquisarPorAvaliacao(Avaliacao avaliacao) {
		Query query = getEntityManager().createNamedQuery(AvaliacaoComida.NAMED_QUERY_PESQUISAR_COMIDA_POR_AVALIACAO);
		query.setParameter("avaliacaoId", avaliacao.getId());
		return query.getResultList();
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
