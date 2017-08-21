package br.com.vibbra.avalieweb.business.imp;

import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.business.AvaliacaoBebidaBusiness;
import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoBebida;
import br.com.vibbra.avalieweb.persistence.AvaliacaoBebidaDAO;

@Name("avaliacaoBebidaBusiness")
@AutoCreate
public class AvaliacaoBebidaBusinessImp extends GenericBusinessImp<AvaliacaoBebida, AvaliacaoBebidaDAO> implements AvaliacaoBebidaBusiness {
	
	private static final long serialVersionUID = 5770779131053537157L;
	
	@In
	AvaliacaoBebidaDAO avaliacaoBebidaDAO;
	
	@Override
	public List<AvaliacaoBebida> pesquisarPorAvaliacao(Avaliacao avaliacao) {
		return getDao().pesquisarPorAvaliacao(avaliacao);
	}
	
	@Override
	public void excluirBebidaPorAvaliacao(Avaliacao avaliacao) {
		List<AvaliacaoBebida> listaAvaliacaoBebida = pesquisarPorAvaliacao(avaliacao);
		if (listaAvaliacaoBebida != null && listaAvaliacaoBebida.size() > 0){
			removeAll(listaAvaliacaoBebida);
		}
		
	}

	@Override
	public AvaliacaoBebidaDAO getDao() {
		return avaliacaoBebidaDAO;
	}

}
