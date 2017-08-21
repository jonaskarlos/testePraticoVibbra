package br.com.vibbra.avalieweb.business;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoBebida;

public interface AvaliacaoBebidaBusiness extends GenericBusiness<AvaliacaoBebida> {
	
	public List<AvaliacaoBebida> pesquisarPorAvaliacao(Avaliacao avaliacao);
	
	public void excluirBebidaPorAvaliacao(Avaliacao avaliacao);

}
