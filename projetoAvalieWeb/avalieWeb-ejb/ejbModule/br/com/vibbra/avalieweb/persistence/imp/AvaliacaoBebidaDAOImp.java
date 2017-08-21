package br.com.vibbra.avalieweb.persistence.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoBebida;
import br.com.vibbra.avalieweb.persistence.AvaliacaoBebidaDAO;

@Name("avaliacaoBebidaDAO")
@AutoCreate
public class AvaliacaoBebidaDAOImp extends GenericDAOImp<AvaliacaoBebida> implements AvaliacaoBebidaDAO {
	
	private static final long serialVersionUID = 4548775530315009732L;
	
	@In
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AvaliacaoBebida> pesquisarPorAvaliacao(Avaliacao avaliacao) {
		Query query = getEntityManager().createNamedQuery(AvaliacaoBebida.NAMED_QUERY_PESQUISAR_BEBIDA_POR_AVALIACAO);
		query.setParameter("avaliacaoId", avaliacao.getId());
		return query.getResultList();
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
