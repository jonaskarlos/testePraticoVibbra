package br.com.vibbra.avalieweb.business;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoComida;

public interface AvaliacaoComidaBusiness extends GenericBusiness<AvaliacaoComida> {
	
	public List<AvaliacaoComida> pesquisarPorAvaliacao(Avaliacao avaliacao);
	
	public void excluirComidaPorAvaliacao(Avaliacao avaliacao);

}
