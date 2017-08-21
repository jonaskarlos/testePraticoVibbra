package br.com.vibbra.avalieweb.business.imp;

import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.business.AvaliacaoComidaBusiness;
import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoComida;
import br.com.vibbra.avalieweb.persistence.AvaliacaoComidaDAO;

@Name("avaliacaoComidaBusiness")
@AutoCreate
public class AvaliacaoComidaBusinessImp extends GenericBusinessImp<AvaliacaoComida, AvaliacaoComidaDAO> implements AvaliacaoComidaBusiness {

	private static final long serialVersionUID = 4860713388128927619L;
	
	@In
	AvaliacaoComidaDAO avaliacaoComidaDAO;
	
	@Override
	public List<AvaliacaoComida> pesquisarPorAvaliacao(Avaliacao avaliacao) {
		return getDao().pesquisarPorAvaliacao(avaliacao);
	}
	
	@Override
	public void excluirComidaPorAvaliacao(Avaliacao avaliacao) {
		List<AvaliacaoComida> listaAvaliacaoComida = pesquisarPorAvaliacao(avaliacao);
		if (listaAvaliacaoComida != null && listaAvaliacaoComida.size() > 0){
			removeAll(listaAvaliacaoComida);
		}
	}

	@Override
	public AvaliacaoComidaDAO getDao() {
		return avaliacaoComidaDAO;
	}

}
