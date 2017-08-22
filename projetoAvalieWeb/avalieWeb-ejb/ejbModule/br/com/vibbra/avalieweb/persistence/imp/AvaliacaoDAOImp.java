package br.com.vibbra.avalieweb.persistence.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;
import br.com.vibbra.avalieweb.persistence.AvaliacaoDAO;
import br.com.vibbra.avalieweb.util.StringUtil;

@Name("avaliacaoDAO")
@AutoCreate
public class AvaliacaoDAOImp extends GenericDAOImp<Avaliacao> implements AvaliacaoDAO {

	private static final long serialVersionUID = -3333551988675428044L;
	
	@In
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Avaliacao> pesquisarAvalicaoPorUsuario(Usuario usuario) {
		Query query = getEntityManager().createNamedQuery(Avaliacao.NAMED_QUERY_PESQUISAR_AVALIACAO_POR_USUARIO);
		query.setParameter("usuarioId", usuario.getId());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Avaliacao> pesquisarAvaliacaoPorUsuarioTipoEndereco(Usuario usuario, TipoEstabelecimentoEnum tipo, String endereco) {
		String email = (usuario != null ? usuario.getEmail() : null);
				
		if (StringUtil.isEmpty(endereco)){
			endereco = "";
		}
	
		endereco = "%"+endereco+"%";
		endereco = endereco.toUpperCase();
		
		Query query = getEntityManager().createNamedQuery(Avaliacao.NAMED_QUERY_PESQUISAR_POR_USUARIO_TIPO_ENDERECO);
		query.setParameter("emailAux", email);
		query.setParameter("email", email);
		query.setParameter("tipoAux", tipo);
		query.setParameter("tipo", tipo);
		query.setParameter("enderecoAux", endereco);
		query.setParameter("endereco", endereco);
		
		return query.getResultList();
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
