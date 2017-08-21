package br.com.vibbra.avalieweb.persistence;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoComida;

public interface AvaliacaoComidaDAO extends GenericDAO<AvaliacaoComida> {
	
	public List<AvaliacaoComida> pesquisarPorAvaliacao(Avaliacao avaliacao);

}
